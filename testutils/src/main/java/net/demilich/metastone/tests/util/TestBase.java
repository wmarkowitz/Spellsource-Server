package net.demilich.metastone.tests.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import co.paralleluniverse.fibers.Suspendable;
import com.google.common.collect.Multiset;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.*;
import net.demilich.metastone.game.behaviour.Behaviour;
import net.demilich.metastone.game.cards.Attribute;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.decks.GameDeck;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityZone;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.Enchantment;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;
import net.demilich.metastone.game.targeting.Zones;
import org.mockito.ArgumentMatchers;
import org.mockito.MockingDetails;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class TestBase {

	protected static Card playChooseOneCard(GameContext context, Player player, String baseCardId, String chosenCardId) {
		return playChooseOneCard(context, player, baseCardId, chosenCardId, null);
	}

	protected static Card playChooseOneCard(GameContext context, Player player, String baseCardId, String chosenCardId, Entity target) {
		Card baseCard = receiveCard(context, player, baseCardId);
		int cost = CardCatalogue.getCardById(chosenCardId).getManaCost(context, player);
		player.setMana(cost);
		GameAction action = context.getValidActions()
				.stream()
				.filter(ga -> ga instanceof PlayChooseOneCardAction)
				.map(ga -> (PlayChooseOneCardAction) ga)
				.filter(coca -> coca.getChoiceCardId().equals(chosenCardId))
				.findFirst()
				.orElseThrow(AssertionError::new);
		if (target != null) {
			action.setTarget(target);
		}
		context.performAction(player.getId(), action);
		return baseCard;
	}

	protected static <T extends Card> T putOnTopOfDeck(GameContext context, Player player, String cardId) {
		final T card = (T) CardCatalogue.getCardById(cardId);
		context.getLogic().insertIntoDeck(player, card, player.getDeck().size());
		return card;
	}

	protected static void destroy(GameContext context, Actor target) {
		target.getAttributes().put(Attribute.DESTROYED, true);
		context.getLogic().endOfSequence();
	}

	protected static void assertAdapted(String name, Minion minion) {
		if (name.equals("Crackling Shield")) {
			Assert.assertTrue(minion.hasAttribute(Attribute.DIVINE_SHIELD));
		} else if (name.equals("Flaming Claws")) {
			Assert.assertEquals(minion.getAttack(), minion.getBaseAttack() + 3);
		} else if (name.equals("Lightning Speed")) {
			Assert.assertTrue(minion.hasAttribute(Attribute.WINDFURY));
		} else if (name.equals("Liquid Membrane")) {
			Assert.assertTrue(minion.hasAttribute(Attribute.UNTARGETABLE_BY_SPELLS));
		} else if (name.equals("Living Spores")) {
			Assert.assertNotNull(minion.getDeathrattles());
			Assert.assertEquals(minion.getDeathrattles().size(), 1);
		} else if (name.equals("Massive")) {
			Assert.assertTrue(minion.hasAttribute(Attribute.TAUNT));
		} else if (name.equals("Poison Spit")) {
			Assert.assertTrue(minion.hasAttribute(Attribute.POISONOUS));
		} else if (name.equals("Rocky Carapace")) {
			Assert.assertEquals(minion.getHp(), minion.getBaseHp() + 3);
		} else if (name.equals("Shrouding Mist")) {
			Assert.assertTrue(minion.hasAttribute(Attribute.STEALTH));
		} else if (name.equals("Volcanic Might")) {
			Assert.assertEquals(minion.getHp(), minion.getBaseHp() + 1);
			Assert.assertEquals(minion.getAttack(), minion.getBaseAttack() + 1);
		}
	}

	protected static void assertNotAdapted(String name, Minion minion) {
		try {
			assertAdapted(name, minion);
		} catch (AssertionError ex) {
			return;
		}
		throw new AssertionError("Adapted");
	}

	protected static void overrideMissilesTrigger(GameContext context, Entity source, Entity target) {
		Enchantment enchantment = (Enchantment) context.getTriggersAssociatedWith(source.getReference()).get(0);
		SpellDesc spell = enchantment.getSpell().clone();
		spell.remove(SpellArg.RANDOM_TARGET);
		spell.setTarget(target.getReference());
		enchantment.setSpell(spell);
	}

	protected static OverrideHandle<Card> overrideRandomCard(GameContext context, String cardId) {
		OverrideHandle<Card> handle = new OverrideHandle<>();
		MockingDetails mockingDetails = Mockito.mockingDetails(context.getLogic());
		GameLogic spyLogic = mockingDetails.isSpy() ? context.getLogic() : Mockito.spy(context.getLogic());
		context.setLogic(spyLogic);
		Answer answer = invocation -> {
			handle.set(CardCatalogue.getCardById(cardId));
			if (!handle.stopped.get()) {
				return handle.get();
			} else {
				return invocation.callRealMethod();
			}
		};

		Mockito.doAnswer(answer).when(spyLogic).getRandom(Mockito.anyList());
		Mockito.doAnswer(answer).when(spyLogic).removeRandom(Mockito.any(Multiset.class));
		Mockito.doAnswer(answer).when(spyLogic).removeRandom(Mockito.anyList());
		return handle;
	}

	protected static OverrideHandle<Card> overrideDiscover(GameContext context, Player player, Function<List<DiscoverAction>, GameAction> discovery) {
		Behaviour overriden = Mockito.spy(context.getBehaviours().get(player.getId()));
		context.setBehaviour(player.getId(), overriden);
		OverrideHandle<Card> handle = new OverrideHandle<>();
		Mockito.doAnswer(invocation -> {
			List<GameAction> actions = invocation.getArgument(2);
			if (actions.stream().allMatch(a -> a instanceof DiscoverAction)
					&& !handle.stopped.get()) {
				List<DiscoverAction> discoveries = new ArrayList<>();
				actions.forEach(a -> discoveries.add((DiscoverAction) a));
				DiscoverAction result = (DiscoverAction) discovery.apply(discoveries);
				handle.set(result.getCard());
				return result;
			} else {
				return invocation.callRealMethod();
			}
		}).when(overriden).requestAction(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyList());
		return handle;
	}

	protected static OverrideHandle<EntityReference> overrideBattlecry(GameContext context, Player player, Function<List<BattlecryAction>, GameAction> battlecry) {
		Behaviour overriden = Mockito.spy(context.getBehaviours().get(player.getId()));
		context.setBehaviour(player.getId(), overriden);
		OverrideHandle<EntityReference> handle = new OverrideHandle<>();
		Mockito.doAnswer(invocation -> {
			List<GameAction> actions = invocation.getArgument(2);
			if (actions.stream().allMatch(a -> a instanceof BattlecryAction)
					&& !handle.stopped.get()) {
				List<BattlecryAction> battlecryActions = new ArrayList<>();

				if (handle.get() != null
						&& battlecryActions.stream().anyMatch(ba -> ba.getTargetReference().equals(handle.get()))) {
					return battlecryActions.stream().filter(ba -> ba.getTargetReference().equals(handle.get())).findFirst().orElseThrow(AssertionError::new);
				}

				actions.forEach(a -> battlecryActions.add((BattlecryAction) a));
				BattlecryAction result = (BattlecryAction) battlecry.apply(battlecryActions);
				handle.set(result.getTargetReference());
				return result;
			} else {
				return invocation.callRealMethod();
			}
		}).when(overriden).requestAction(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyList());
		return handle;
	}

	protected static Minion playMinionCardWithBattlecry(GameContext context, Player player, String cardId, Entity target) {
		OverrideHandle<EntityReference> handle = overrideBattlecry(context, player, battlecryActions -> battlecryActions.stream().filter(c -> c.getTargetReference().equals(target.getReference())).findFirst().orElseThrow(AssertionError::new));
		Minion result = playMinionCard(context, player, cardId);
		handle.stop();
		return result;
	}

	protected static Minion playMinionCardWithBattlecry(GameContext context, Player player, Card card, Entity target) {
		OverrideHandle<EntityReference> handle = overrideBattlecry(context, player, battlecryActions -> battlecryActions.stream().filter(c -> c.getTargetReference().equals(target.getReference())).findFirst().orElseThrow(AssertionError::new));
		return playMinionCard(context, player, card);
	}

	protected static void overrideDiscover(GameContext context, Player player, String cardId) {
		OverrideHandle<Card> handle = overrideRandomCard(context, cardId);
		overrideDiscover(context, player, discovers -> {
			DiscoverAction action = discovers.stream().filter(da -> da.getCard().getCardId().equals(cardId)).findFirst().orElseThrow(AssertionError::new);
			handle.stop();
			return action;
		});
	}

	public static <T extends Card> T receiveCard(GameContext context, Player player, String cardId) {
		T card = (T) CardCatalogue.getCardById(cardId);
		context.getLogic().receiveCard(player.getId(), card);
		return card;
	}

	public static <T extends Card> T shuffleToDeck(GameContext context, Player player, String cardId) {
		T card = (T) CardCatalogue.getCardById(cardId);
		context.getLogic().shuffleToDeck(player, card);
		return card;
	}

	public static int costOf(GameContext context, Player player, Card deckCard) {
		return context.getLogic().getModifiedManaCost(player, deckCard);
	}

	@FunctionalInterface
	public interface GymConsumer {
		@Suspendable
		void run(GameContext context, Player player, Player opponent);

		@Suspendable
		default GymConsumer andThen(GymConsumer after) {
			Objects.requireNonNull(after);
			return (c, p, o) -> {
				run(c, p, o);
				after.run(c, p, o);
			};
		}
	}

	public static GymFactory getGymFactory(GymConsumer initializer) {
		GymFactory factory = new GymFactory();
		factory.first = initializer;
		return factory;
	}

	public static GymFactory getGymFactory(GymConsumer initializer, GymConsumer after) {
		GymFactory factory = getGymFactory(initializer);
		factory.after = after;
		return factory;
	}

	@Suspendable
	public static void runGym(GymConsumer consumer, HeroClass heroClass1, HeroClass heroClass2) {
		GameContext context = createContext(heroClass1, heroClass2, true, new DeckFormat().withCardSets(CardSet.BASIC, CardSet.CLASSIC));
		Player player = context.getActivePlayer();
		Player opponent = context.getOpponent(player);
		clearHand(context, player);
		clearHand(context, opponent);
		clearZone(context, player.getDeck());
		clearZone(context, opponent.getDeck());
		clearZone(context, player.getGraveyard());
		clearZone(context, opponent.getGraveyard());
		context.setDeckFormat(DeckFormat.CUSTOM);

		consumer.run(context, player, opponent);
	}

	public static void runGym(GymConsumer consumer, GameDeck deck1, GameDeck deck2) {
		GameContext context = new DebugContext(new Player(deck1), new Player(deck2), new GameLogic() {
			@Override
			public int determineBeginner(int... playerIds) {
				return 0;
			}

			@Override
			protected int getStarterCards() {
				return 0;
			}

			@Override
			protected int getSecondPlayerBonusStarterCards() {
				return 0;
			}
		}, DeckFormat.getSmallestSupersetFormat(deck1, deck2));
		context.setBehaviours(new Behaviour[]{new TestBehaviour(), new TestBehaviour()});
		context.init();
		consumer.run(context, context.getActivePlayer(), context.getOpponent(context.getActivePlayer()));
	}

	@Suspendable
	public static void runGym(GymConsumer consumer) {
		runGym(consumer, HeroClass.BLUE, HeroClass.BLUE);
	}

	public static void clearHand(GameContext context, Player player) {
		for (int i = player.getHand().getCount() - 1; i >= 0; i--) {
			context.getLogic().removeCard(player.getHand().get(i));
		}
	}

	public static void clearZone(GameContext context, EntityZone<? extends Entity> zone) {
		if (zone.getZone() == Zones.GRAVEYARD) {
			for (int i = zone.size() - 1; i >= 0; i--) {
				zone.get(i).moveOrAddTo(context, Zones.REMOVED_FROM_PLAY);
			}

			return;
		}

		for (int i = zone.size() - 1; i >= 0; i--) {
			Entity entity = zone.get(i);
			if (Card.class.isAssignableFrom(entity.getClass())) {
				context.getLogic().removeCard((Card) entity);
			} else if (Actor.class.isAssignableFrom(entity.getClass())) {
				context.getLogic().destroy((Actor) entity);
			} else {
				entity.moveOrAddTo(context, Zones.REMOVED_FROM_PLAY);
			}
		}
	}

	public static <A, B, C> Stream<C> zip(Stream<? extends A> a,
	                                      Stream<? extends B> b,
	                                      BiFunction<? super A, ? super B, ? extends C> zipper) {
		Objects.requireNonNull(zipper);
		Spliterator<? extends A> aSpliterator = Objects.requireNonNull(a).spliterator();
		Spliterator<? extends B> bSpliterator = Objects.requireNonNull(b).spliterator();

		// Zipping looses DISTINCT and SORTED characteristics
		int characteristics = aSpliterator.characteristics() & bSpliterator.characteristics() &
				~(Spliterator.DISTINCT | Spliterator.SORTED);

		long zipSize = ((characteristics & Spliterator.SIZED) != 0)
				? Math.min(aSpliterator.getExactSizeIfKnown(), bSpliterator.getExactSizeIfKnown())
				: -1;

		Iterator<A> aIterator = Spliterators.iterator(aSpliterator);
		Iterator<B> bIterator = Spliterators.iterator(bSpliterator);
		Iterator<C> cIterator = new Iterator<C>() {
			@Override
			public boolean hasNext() {
				return aIterator.hasNext() && bIterator.hasNext();
			}

			@Override
			public C next() {
				return zipper.apply(aIterator.next(), bIterator.next());
			}
		};

		Spliterator<C> split = Spliterators.spliterator(cIterator, zipSize, characteristics);
		return (a.isParallel() || b.isParallel())
				? StreamSupport.stream(split, true)
				: StreamSupport.stream(split, false);
	}

	@BeforeMethod
	public void loadCards() throws Exception {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.DEBUG);
		CardCatalogue.loadCardsFromPackage();
	}

	protected static void attack(GameContext context, Player player, Entity attacker, Entity target) {
		PhysicalAttackAction physicalAttackAction = new PhysicalAttackAction(attacker.getReference());
		physicalAttackAction.setTarget(target);
		context.performAction(player.getId(), physicalAttackAction);
	}

	public static DebugContext createContext(HeroClass hero1, HeroClass hero2) {
		return createContext(hero1, hero2, true, DeckFormat.CUSTOM);
	}

	public static DebugContext createContext(HeroClass hero1, HeroClass hero2, boolean shouldInit, DeckFormat deckFormat) {
		Player player1 = new Player(Deck.randomDeck(hero1, deckFormat), "Player 1");
		Player player2 = new Player(Deck.randomDeck(hero2, deckFormat), "Player 2");

		DebugContext context = new DebugContext(player1, player2, new GameLogic() {
			@Override
			public int determineBeginner(int... playerIds) {
				return GameContext.PLAYER_1;
			}
		}, deckFormat);
		context.setBehaviours(new Behaviour[]{new TestBehaviour(), new TestBehaviour()});
		if (shouldInit) {
			context.init();
		}
		return context;
	}

	protected static Entity find(GameContext context, String cardId) {
		for (Player player : context.getPlayers()) {
			for (Minion minion : player.getMinions()) {
				if (minion.getSourceCard().getCardId().equals(cardId)) {
					return minion;
				}
			}
		}
		return null;
	}

	protected static Entity findCard(GameContext context, String cardId) {
		for (Player player : context.getPlayers()) {
			for (Card card : player.getHand()) {
				if (card.getSourceCard().getCardId().equals(cardId)) {
					return card;
				}
			}
		}
		return null;
	}

	protected static Actor getSingleMinion(List<Minion> minions) {
		for (Actor minion : minions) {
			if (minion == null) {
				continue;
			}
			return minion;
		}
		return null;
	}

	protected static Minion getSummonedMinion(List<Minion> minions) {
		List<Minion> minionList = new ArrayList<>(minions);
		Collections.sort(minionList, Comparator.comparingInt(Entity::getId));
		return minionList.get(minionList.size() - 1);
	}

	@Suspendable
	protected static void playCard(GameContext context, Player player, String cardId) {
		playCard(context, player, CardCatalogue.getCardById(cardId));
	}

	@Suspendable
	protected static void playCard(GameContext context, Player player, Card card) {
		if (card.getZone() != Zones.HAND) {
			context.getLogic().receiveCard(player.getId(), card);
		}
		if (card.getTargetSelection() != TargetSelection.NONE && card.getTargetSelection() != null) {
			throw new UnsupportedOperationException(String.format("This card %s requires a target.", card.getName()));
		}
		context.performAction(player.getId(), card.play());
	}

	@Suspendable
	protected static void useHeroPower(GameContext context, Player player) {
		context.performAction(player.getId(), player.getHero().getHeroPower().play());
	}

	@Suspendable
	protected static void useHeroPower(GameContext context, Player player, EntityReference target) {
		PlayCardAction action = player.getHero().getHeroPower().play();
		action.setTargetReference(target);
		context.performAction(player.getId(), action);
	}

	protected static void playCard(GameContext context, Player player, String cardId, Entity target) {
		playCard(context, player, CardCatalogue.getCardById(cardId), target);
	}

	protected static void playCard(GameContext context, Player player, Card card, Entity target) {
		if (card.getZone() != Zones.HAND) {
			context.getLogic().receiveCard(player.getId(), card);
		}
		if (!target.isInPlay()) {
			throw new UnsupportedOperationException("cannot target not in play entities");
		}
		GameAction action = card.play();
		action.setTarget(target);
		context.performAction(player.getId(), action);
	}

	protected static Minion playMinionCard(GameContext context, Player player, String minionCardId) {
		return playMinionCard(context, player, CardCatalogue.getCardById(minionCardId));
	}

	protected static Minion playMinionCard(GameContext context, Player player, Card card) {
		if (card.getZone() != Zones.HAND) {
			context.getLogic().receiveCard(player.getId(), card);
		}

		PlayCardAction play = card.isChooseOne() ? card.playOptions()[0] : card.play();
		context.performAction(player.getId(), play);
		return getSummonedMinion(player.getMinions());
	}


}
