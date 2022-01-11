package icu.nullptr.stringfuck.unittest

import androidx.test.ext.junit.runners.AndroidJUnit4
import icu.nullptr.stringfuck.StringFuck
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreInitTest {

    @Test
    fun preInitTest() {
        Assert.assertNotEquals(TestStrings.test0, "TEST0")
        Assert.assertNotEquals(TestStrings.test1, "TEST1")
        Assert.assertNotEquals(TestStrings().test2, "TEST2")
        Assert.assertNotEquals(TestStrings.functionTest1(), "TEST3")
        Assert.assertNotEquals(TestStrings().functionTest2(), "TEST4")
    }
}

@RunWith(AndroidJUnit4::class)
class PostInitTest {

    @Test
    fun postInitTest() {
        StringFuck.init()
        Assert.assertEquals(TestStrings.test0, "TEST0")
        Assert.assertEquals(TestStrings.test1, "TEST1")
        Assert.assertEquals(TestStrings().test2, "TEST2")
        Assert.assertEquals(TestStrings.functionTest1(), "TEST3")
        Assert.assertEquals(TestStrings().functionTest2(), "TEST4")
    }
}
