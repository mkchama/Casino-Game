package core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	Player_Test.class,
	Hand_Test.class,
	Card_Test.class,
	Deck_Test.class
})

/**
 * This is an empty class that is used to run the core junit tests under one suite,
 * i.e. a single command rather than multiple
 */

public class CoreTestSuite {}