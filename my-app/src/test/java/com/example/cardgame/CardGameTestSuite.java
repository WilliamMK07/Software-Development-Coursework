package com.example.cardgame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite; 
public class CardGameTestSuite {
@RunWith(Suite.class)
@Suite.SuiteClasses({TestCardclass.class, TestDeckClass.class, TestCardGameClass.class, TestGameclass.class})
public class TestSuite {
// empty , as the class is just a holder
// for the annotations above
} 
}
