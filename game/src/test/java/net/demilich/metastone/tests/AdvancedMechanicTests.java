package net.demilich.metastone.tests;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.DiscoverAction;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.actions.PhysicalAttackAction;
import net.demilich.metastone.game.behaviour.UtilityBehaviour;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.HasChooseOneActions;
import net.demilich.metastone.game.decks.FixedCardsDeckFormat;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.TurnEndEvent;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.spells.SetHpSpell;
import net.demilich.metastone.game.spells.SilenceSpell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.*;
import net.demilich.metastone.game.targeting.TargetSelection;
import net.demilich.metastone.game.cards.Attribute;
import net.demilich.metastone.game.targeting.Zones;
import net.demilich.metastone.tests.util.GymFactory;
import net.demilich.metastone.tests.util.TestBase;
import net.demilich.metastone.tests.util.TestMinionCard;
import net.demilich.metastone.tests.util.TestSpellCard;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.testng.Assert.*;

public class AdvancedMechanicTests extends TestBase {

	@Test
	public void testReshuffle() {
		runGym((context, player, opponent) -> {
			Card inHand = receiveCard(context, player, "spell_empty_card_1");
			playCard(context, player, "spell_test_reshuffle");
			assertEquals(inHand.getZone(), Zones.DECK);
			assertEquals(player.getHand().size(), 0);
		});
	}

	@Test
	public void testWitherShouldNotTriggerOnDivineShield() {
		runGym((context, player, opponent) -> {
			Minion target = playMinionCard(context, player, "minion_divine_shield_test");
			playCard(context, player, "spell_wither_test", target);
			assertEquals(target.getAttack(), target.getBaseAttack());
			assertEquals(target.getHp(), target.getMaxHp());
			assertEquals(target.getHp(), target.getBaseHp());
			assertFalse(target.hasAttribute(Attribute.DIVINE_SHIELD));
		});
	}

	@Test
	public void testCopyingEnchantments() {
		runGym((context, player, opponent) -> {
			// Should give us exactly two auras
			Minion auraMinion = playMinionCard(context, player, "minion_test_aura");
			Minion copy = playMinionCardWithBattlecry(context, player, "minion_test_copy", auraMinion);
			assertEquals(copy.getSourceCard().getCardId(), "minion_test_aura");
			assertEquals(player.getMinions().size(), 2);
			assertEquals(auraMinion.getAttack(), 4);
			assertEquals(copy.getAttack(), 4);
			assertEquals(context.getTriggerManager().getTriggers().size(), 2);
		});

		runGym((context, player, opponent) -> {
			// Should give us exactly two card cost modifiers
			Minion radiant = playMinionCard(context, player, "minion_radiant_elemental");
			Minion princeTaldaramTransformed = playMinionCardWithBattlecry(context, player, "minion_prince_taldaram", radiant);
			assertEquals(princeTaldaramTransformed.getSourceCard().getCardId(), "minion_radiant_elemental");
			Card costThreeSpell = receiveCard(context, player, "spell_blood_warriors");
			assertEquals(costOf(context, player, costThreeSpell), costThreeSpell.getBaseManaCost() - 2);
		});

		runGym((context, player, opponent) -> {
			// Should correctly copy enchantments granted after the fact
			Minion blessed = playMinionCard(context, player, "minion_neutral_test");
			playCard(context, player, "spell_blessing_of_wisdom", blessed);
			Minion copy = playMinionCardWithBattlecry(context, player, "minion_test_copy", blessed);
			context.endTurn();
			context.endTurn();
			for (int i = 0; i < 10; i++) {
				shuffleToDeck(context, player, "spell_the_coin");
			}
			attack(context, player, blessed, opponent.getHero());
			assertEquals(player.getHand().size(), 1);
			attack(context, player, copy, opponent.getHero());
			assertEquals(player.getHand().size(), 2);
		});

		runGym((context, player, opponent) -> {
			// Copies with persistent owner should work
			Minion blessed = playMinionCard(context, player, "minion_neutral_test");
			context.endTurn();
			playCard(context, opponent, "spell_blessing_of_wisdom", blessed);
			context.endTurn();
			Minion copy = playMinionCardWithBattlecry(context, player, "minion_test_copy", blessed);
			context.endTurn();
			context.endTurn();
			for (int i = 0; i < 10; i++) {
				shuffleToDeck(context, opponent, "spell_the_coin");
			}
			attack(context, player, blessed, opponent.getHero());
			assertEquals(opponent.getHand().size(), 1);
			attack(context, player, copy, opponent.getHero());
			assertEquals(opponent.getHand().size(), 2);
		});
	}

	@Test
	public void testSplashDamageAppliesPoisonousAndLifesteal() {
		runGym((context, player, opponent) -> {
			playCard(context, player, "weapon_splash_damage_weapon");
			context.endTurn();
			Minion target1 = playMinionCard(context, opponent, "minion_neutral_test");
			Minion target2 = playMinionCard(context, opponent, "minion_neutral_test");
			Minion target3 = playMinionCard(context, opponent, "minion_neutral_test");
			context.endTurn();
			player.getHero().setHp(27);
			attack(context, player, player.getHero(), target2);
			assertTrue(target1.isDestroyed());
			assertTrue(target2.isDestroyed());
			assertTrue(target3.isDestroyed());
			assertEquals(player.getHero().getHp(), 30 - target2.getAttack());
		});
	}

	@Test
	public void testPermanentDoesntTriggerSummons() {
		runGym((context, player, opponent) -> {
			Minion minion = playMinionCard(context, player, "minion_test_permanents_dont_trigger_summons");
			playCard(context, player, "permanent_test");
			assertEquals(minion.getAttack(), minion.getBaseAttack(), "Should not have buffed");
			playCard(context, player, "minion_neutral_test");
			assertEquals(minion.getAttack(), minion.getBaseAttack() + 1, "Should have buffed");
		});
	}

	@Test
	public void testSecrets() {
		runGym((context, player, opponent) -> {
			// Player cannot play multiples of the same secrets
			playCard(context, player, "secret_counterspell");
			Card counterspell = receiveCard(context, player, "secret_counterspell");
			player.setMana(3);
			assertFalse(context.getValidActions().stream().anyMatch(p -> p.getActionType() == ActionType.SPELL
					&& p.getSourceReference().equals(counterspell.getReference())));
		});
	}

	@Test
	public void testDeflect() {
		runGym((context, player, opponent) -> {
			int hp = player.getHero().getHp();
			Minion deflect = playMinionCard(context, player, "minion_test_deflect");
			assertTrue(deflect.hasAttribute(Attribute.DEFLECT));
			playCard(context, player, "spell_fireball", deflect);
			assertFalse(deflect.hasAttribute(Attribute.DEFLECT));
			assertFalse(deflect.isDestroyed());
			assertEquals(player.getHero().getHp(), hp - 6);
		});

		runGym((context, player, opponent) -> {
			Minion deflect = playMinionCard(context, player, "minion_test_deflect");
			assertTrue(deflect.hasAttribute(Attribute.DEFLECT));
			context.endTurn();
			Minion attacker = playMinionCard(context, opponent, "minion_charge_test");
			int hp = player.getHero().getHp();
			attack(context, opponent, attacker, deflect);
			assertFalse(deflect.hasAttribute(Attribute.DEFLECT));
			assertFalse(deflect.isDestroyed());
			assertEquals(player.getHero().getHp(), hp - attacker.getAttack());
		});

		runGym((context, player, opponent) -> {
			Minion deflect = playMinionCard(context, player, "minion_test_deflect");
			assertTrue(deflect.hasAttribute(Attribute.DEFLECT));
			context.endTurn();
			Minion defender = playMinionCard(context, opponent, "minion_charge_test");
			context.endTurn();

			int hp = player.getHero().getHp();
			attack(context, player, deflect, defender);
			assertTrue(defender.isDestroyed());
			assertFalse(deflect.isDestroyed());
			assertFalse(deflect.hasAttribute(Attribute.DEFLECT));
			assertEquals(player.getHero().getHp(), hp - defender.getAttack());
		});
	}

	@Test
	public void testCardFilter() {
		runGym((context, player, opponent) -> {
			Card hasTaunt = CardCatalogue.getCardById("minion_test_taunts");
			EntityFilterDesc desc = new EntityFilterDesc(CardFilter.class);
			desc.put(EntityFilterArg.CARD_TYPE, CardType.MINION);
			desc.put(EntityFilterArg.ATTRIBUTE, Attribute.TAUNT);
			desc.put(EntityFilterArg.OPERATION, ComparisonOperation.HAS);
			EntityFilter filter = desc.create();
			assertTrue(filter.matches(context, player, hasTaunt, player.getHero()));
		});

	}

	@Test
	public void testCreateCardFromChoices() {
		runGym((context, player, opponent) -> {
			playCard(context, player, "spell_test_taunt_first_choices");
			Minion target = playMinionCard(context, player, player.getHand().get(0));
			assertTrue(target.hasAttribute(Attribute.TAUNT));
		});

		runGym((context, player, opponent) -> {
			playCard(context, player, "spell_test_taunt_second_choices");
			Minion target = playMinionCard(context, player, player.getHand().get(0));
			assertTrue(target.hasAttribute(Attribute.TAUNT));
		});
	}

	@Test
	public void testShuffleToDeck() {
		runGym((context, player, opponent) -> {
			playCard(context, player, "spell_shuffle_to_deck");
			assertEquals(player.getDeck().size(), 9);
			String[] cards = (String[]) CardCatalogue.getCardById("spell_shuffle_to_deck").getDesc().getSpell().get(SpellArg.CARDS);

			for (int i = 0; i < cards.length; i++) {
				if (!player.getDeck().get(i).getCardId().equals(cards[i])) {
					return;
				}
			}

			Assert.fail();
		});
	}

	@Test
	public void testRush() {
		runGym((context, player, opponent) -> {
			context.endTurn();
			Minion opponentMinion = playMinionCard(context, opponent, "minion_black_test");
			context.endTurn();
			Minion rushMinion = playMinionCard(context, player, "minion_test_rush");
			assertTrue(rushMinion.canAttackThisTurn());
			assertTrue(context.getValidActions().stream().noneMatch(c -> c.getActionType() == ActionType.PHYSICAL_ATTACK
					&& c.getTargetReference().equals(opponent.getHero().getReference())));
			assertTrue(context.getValidActions().stream().anyMatch(c -> c.getActionType() == ActionType.PHYSICAL_ATTACK
					&& c.getTargetReference().equals(opponentMinion.getReference())));
			context.endTurn();
			context.endTurn();
			assertTrue(context.getValidActions().stream().anyMatch(c -> c.getActionType() == ActionType.PHYSICAL_ATTACK
					&& c.getTargetReference().equals(opponent.getHero().getReference())));
			assertTrue(context.getValidActions().stream().anyMatch(c -> c.getActionType() == ActionType.PHYSICAL_ATTACK
					&& c.getTargetReference().equals(opponentMinion.getReference())));
		});

		runGym((context, player, opponent) -> {
			context.endTurn();
			Minion opponentMinion = playMinionCard(context, opponent, "minion_black_test");
			context.endTurn();
			Minion rushMinion = playMinionCard(context, player, "minion_test_rush");
			rushMinion.setAttribute(Attribute.CHARGE);
			assertTrue(rushMinion.canAttackThisTurn());
			assertTrue(context.getValidActions().stream().anyMatch(c -> c.getActionType() == ActionType.PHYSICAL_ATTACK
					&& c.getTargetReference().equals(opponent.getHero().getReference())));
			assertTrue(context.getValidActions().stream().anyMatch(c -> c.getActionType() == ActionType.PHYSICAL_ATTACK
					&& c.getTargetReference().equals(opponentMinion.getReference())));
		});
	}

	@Test
	public void testEndTurnEventAppearsOnce() {
		runGym((context, player, opponent) -> {
			context = spy(context);
			context.getLogic().setContext(context);
			AtomicInteger counter = new AtomicInteger(0);
			doAnswer(invocation -> {
				GameEvent event = invocation.getArgument(0);
				if (event instanceof TurnEndEvent) {
					counter.incrementAndGet();
				}
				return invocation.callRealMethod();
			}).when(context).fireGameEvent(any());
			context.endTurn();
			assertEquals(counter.get(), 1);
		});
	}

	@Test
	public void testDiscover() {
		GymFactory factory = getGymFactory((context, player, opponent) -> {
			player.getRemovedFromPlay().clear();
			opponent.getRemovedFromPlay().clear();
		});

		// Test basic discover
		// Black, blue, gold
		HeroClass[] classes = new HeroClass[]{HeroClass.BLACK, HeroClass.BLUE, HeroClass.GOLD};

		// Tests that the discover comes in the exact order the author requested when howMany == choices
		for (int j = 0; j < 3; j++) {
			final int i = j;
			factory.run((context, player, opponent) -> {
				overrideDiscover(context, player, discoverActions -> {
					assertEquals(discoverActions.size(), 3);
					assertEquals(player.getDiscoverZone().size(), 3);
					return discoverActions.get(i);
				});

				playCard(context, player, "spell_test_discover1");
				assertEquals(player.getHand().get(0).getHeroClass(), classes[i]);
				assertEquals(player.getDiscoverZone().size(), 0);
				assertEquals(player.getRemovedFromPlay().size(), 3);
			});
		}

		// Chooses 3 of the four specified cards
		// Assure that you were shown 3 choices but that all four were looked at
		factory.run((context, player, opponent) -> {
			GameLogic spyLogic = spy(context.getLogic());
			context.setLogic(spyLogic);
			AtomicInteger size = new AtomicInteger(4);
			doAnswer(invocation -> {
				List<Card> options = invocation.getArgument(0);
				assertEquals(options.size(), size.getAndDecrement());
				return invocation.callRealMethod();
			}).when(spyLogic).removeRandom(anyList());
			playCard(context, player, "spell_test_discover2");
			assertEquals(size.get(), 4 - 3, "There should be one discover option left out");
			assertEquals(player.getDiscoverZone().size(), 0);
			assertEquals(player.getRemovedFromPlay().size(), 3, "Only generated cards should have been removed from play.");
		});

		// Doesn't show duplicates due to class card weighting
		factory.run((context, player, opponent) -> {
			context.setDeckFormat(new FixedCardsDeckFormat("minion_blue_test", "minion_neutral_test"));
			overrideDiscover(context, player, discoverActions -> {
				assertEquals(discoverActions.stream().map(DiscoverAction::getCard).map(Card::getCardId).distinct().count(), 2L);
				return discoverActions.get(0);
			});
			playCard(context, player, "spell_test_discover3");
			assertEquals(player.getHand().size(), 1);
		});
	}

	@Test
	public void testChooseOne() {
		GameContext context = createContext(HeroClass.BROWN, HeroClass.RED);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();

		context.endTurn();
		TestMinionCard Card = new TestMinionCard(1, 4);
		playCard(context, opponent, Card);
		context.endTurn();

		player.getHero().getHeroPower().markUsed();
		for (Card card : player.getHand().toList()) {
			context.getLogic().removeCard(card);
		}
		Card wrath = CardCatalogue.getCardById("spell_test_choose_one");
		HasChooseOneActions wrathChooseOne = wrath;
		context.getLogic().receiveCard(player.getId(), wrath);
		player.setMana(wrath.getBaseManaCost() + 1);
		List<GameAction> validActions = context.getLogic().getValidActions(player.getId());
		assertEquals(player.getHand().getCount(), 1);
		// player should have 3 valid actions: two from 'Choose One' card and 1 'End Turn'
		assertEquals(validActions.size(), 3);

		GameAction playWrath = wrathChooseOne.playOptions()[0];
		playWrath.setTarget(getSingleMinion(opponent.getMinions()));
		context.performAction(player.getId(), playWrath);

		validActions = context.getLogic().getValidActions(player.getId());
		// This time it should just be the 'End Turn'
		assertEquals(validActions.size(), 1);
		assertEquals(player.getHand().getCount(), 0);
	}

	@Test
	public void testCopyCards() {
		GameContext context = createContext(HeroClass.WHITE, HeroClass.RED);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();
		clearHand(context, player);

		int cardsInHand = player.getHand().getCount();
		int cardsInOpponentsDeck = opponent.getDeck().getCount();
		Card thoughtsteal = CardCatalogue.getCardById("spell_test_copy_cards");
		context.getLogic().receiveCard(player.getId(), thoughtsteal);
		context.performAction(player.getId(), thoughtsteal.play());
		assertEquals(opponent.getDeck().getCount(), cardsInOpponentsDeck);
		assertEquals(player.getHand().getCount(), cardsInHand + 2);
	}

	@Test
	public void testDivineShield() {
		GameContext context = createContext(HeroClass.BLUE, HeroClass.RED);
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		Card card1 = new TestMinionCard(2, 2, Attribute.DIVINE_SHIELD);
		context.getLogic().receiveCard(mage.getId(), card1);
		context.performAction(mage.getId(), card1.play());

		Card card2 = new TestMinionCard(5, 5);
		context.getLogic().receiveCard(warrior.getId(), card2);
		context.performAction(warrior.getId(), card2.play());

		Actor attacker = getSingleMinion(mage.getMinions());
		Actor defender = getSingleMinion(warrior.getMinions());

		GameAction attackAction = new PhysicalAttackAction(attacker.getReference());
		attackAction.setTarget(defender);

		context.performAction(mage.getId(), attackAction);
		assertEquals(attacker.getHp(), attacker.getMaxHp());
		assertEquals(defender.getHp(), defender.getMaxHp() - attacker.getAttack());
		assertEquals(attacker.isDestroyed(), false);

		context.performAction(mage.getId(), attackAction);
		assertEquals(attacker.getHp(), attacker.getMaxHp() - defender.getAttack());
		assertEquals(defender.getHp(), defender.getMaxHp() - attacker.getAttack() * 2);
		assertEquals(attacker.isDestroyed(), true);
	}

	@Test
	public void testEnrage() {
		runGym((context, player, opponent) -> {
			context.endTurn();
			final int BASE_ATTACK = 2;
			final int ENRAGE_ATTACK_BONUS = 3;
			Minion attacker = playMinionCard(context, opponent, "minion_test_enrage");
			context.endTurn();
			Minion defender1 = playMinionCard(context, player, new TestMinionCard(1, 10));

			assertEquals(attacker.getAttack(), BASE_ATTACK);
			assertFalse(attacker.hasAttribute(Attribute.ENRAGED));
			context.endTurn();
			// attack once, should apply the enrage attack bonus
			attack(context, opponent, attacker, defender1);
			assertEquals(attacker.getAttack(), BASE_ATTACK + ENRAGE_ATTACK_BONUS);
			assertTrue(attacker.hasAttribute(Attribute.ENRAGED));
			// attack second time, enrage bonus should not increase
			attack(context, opponent, attacker, defender1);
			assertEquals(attacker.getAttack(), BASE_ATTACK + ENRAGE_ATTACK_BONUS);

			// heal - enrage attack bonus should be gone
			playCard(context, player, "spell_greater_healing_potion", attacker);
			assertEquals(attacker.getAttack(), BASE_ATTACK);
			assertFalse(attacker.hasAttribute(Attribute.ENRAGED));

			// attack once more - should enrage again
			attack(context, opponent, attacker, defender1);
			assertEquals(attacker.getAttack(), BASE_ATTACK + ENRAGE_ATTACK_BONUS);
			assertTrue(attacker.hasAttribute(Attribute.ENRAGED));

			// attack should be set to 1
			playCard(context, player, "spell_humility", attacker);
			assertEquals(attacker.getAttack(), 1);
			assertTrue(attacker.hasAttribute(Attribute.ENRAGED));
		});
	}

	@Test
	public void testOverload() {
		runGym((context, player, opponent) -> {
			assertEquals(player.getMana(), 1);
			context.endTurn();
			context.endTurn();
			assertEquals(player.getMana(), 2);

			Card overloadCard = new TestMinionCard(1, 1);
			overloadCard.setAttribute(Attribute.OVERLOAD, 2);
			context.getLogic().receiveCard(player.getId(), overloadCard);
			context.performAction(player.getId(), overloadCard.play());
			context.endTurn();
			context.endTurn();
			assertEquals(player.getMana(), 1);

			context.endTurn();
			context.endTurn();
			assertEquals(player.getMana(), 4);
		});

	}

	@Test
	public void testSetHpPlusSilence() {
		GameContext context = createContext(HeroClass.GREEN, HeroClass.RED);
		Player player = context.getPlayer1();
		Player opponent = context.getPlayer2();

		int baseHp = 5;
		// summon a minion and check the base hp
		playCard(context, opponent, new TestMinionCard(4, baseHp));
		Actor minion = getSingleMinion(opponent.getMinions());
		assertEquals(minion.getHp(), baseHp);

		int modifiedHp = 1;
		// cast a spell on the minion which modifies the hp
		SpellDesc setHpSpell = SetHpSpell.create(modifiedHp);
		Card card = new TestSpellCard(setHpSpell);
		card.setTargetRequirement(TargetSelection.MINIONS);
		context.getLogic().receiveCard(player.getId(), card);
		GameAction playSpellCard = card.play();
		playSpellCard.setTarget(minion);
		context.performAction(player.getId(), playSpellCard);
		assertEquals(minion.getHp(), modifiedHp);
		assertEquals(minion.getMaxHp(), modifiedHp);

		// silence the creature - hp should be back to original value
		SpellDesc silenceSpell = SilenceSpell.create();
		card = new TestSpellCard(silenceSpell);
		card.setTargetRequirement(TargetSelection.MINIONS);
		context.getLogic().receiveCard(player.getId(), card);
		playSpellCard = card.play();
		playSpellCard.setTarget(minion);
		context.performAction(player.getId(), playSpellCard);
		assertEquals(minion.getHp(), baseHp);
	}

	@Test
	public void testShorttermBuffs() {
		GameContext context = createContext(HeroClass.BLUE, HeroClass.RED);
		Player mage = context.getPlayer1();
		mage.setMana(10);
		Player warrior = context.getPlayer2();
		warrior.setMana(10);

		int baseAttack = 1;
		context.setBehaviour(0, new UtilityBehaviour() {

			@Override
			public String getName() {
				return "Select-First";
			}

			@Override
			public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
				return new ArrayList<Card>();
			}

			@Override
			public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
				return validActions.get(0);
			}

		});

		playCard(context, mage, new TestMinionCard(baseAttack, 1));
		Actor testSubject = getSingleMinion(mage.getMinions());
		assertEquals(testSubject.getAttack(), baseAttack);

		playCard(context, mage, "minion_test_buffs");
		assertEquals(testSubject.getAttack(), baseAttack + 2);
		context.getLogic().endTurn(mage.getId());
		assertEquals(testSubject.getAttack(), baseAttack);
	}

	@Test
	public void testSpellpower() {
		runGym((context, player, opponent) -> {
			player.setMana(10);
			opponent.setMana(10);

			assertEquals(opponent.getHero().getHp(), opponent.getHero().getMaxHp());
			Card damageSpell = CardCatalogue.getCardById("spell_test_spellpower");
			int expectedDamage = 5;
			context.getLogic().receiveCard(player.getId(), damageSpell);

			context.performAction(player.getId(), damageSpell.play());
			assertEquals(opponent.getHero().getHp(), opponent.getHero().getMaxHp() - expectedDamage);

			Card spellPowerCard = CardCatalogue.getCardById("minion_test_spellpower");
			context.getLogic().receiveCard(player.getId(), spellPowerCard);
			context.performAction(player.getId(), spellPowerCard.play());
			damageSpell = damageSpell.getCopy();
			context.getLogic().receiveCard(player.getId(), damageSpell);
			context.performAction(player.getId(), damageSpell.play());
			int spellPower = getSingleMinion(player.getMinions()).getAttributeValue(Attribute.SPELL_DAMAGE);
			assertEquals(opponent.getHero().getHp(), opponent.getHero().getMaxHp() - 2 * expectedDamage - spellPower);

			int opponentHp = opponent.getHero().getHp();
			GameAction useHeroPower = player.getHero().getHeroPower().play();
			useHeroPower.setTarget(opponent.getHero());
			context.performAction(player.getId(), useHeroPower);

			// hero power should not be affected by SPELL_DAMAGE, and thus deal 1 damage
			assertEquals(opponent.getHero().getHp(), opponentHp - 1);
		});
	}
}
