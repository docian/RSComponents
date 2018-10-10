*********************************************************************
*	Tests for searching and ordering items on RS Components site	*
*********************************************************************
The tests were developed by taking two aproaches:
- Data Driven (DDT) Tests
- Behavior Driven(BDT) Test
Choices were for TestNG as framework and Cucumber as BDT library. The tests
are developed for any of Chrome, Firefox and IE browsers for DDT and Chrome
browser for BDT(but the principle is the same) 

The tests could be ran by TestNG xml run profiles as follows:
testcheckout.xml for DDT and test_checkout_runner.xml for BDT and they are a little 
bit different.
The locators are in JSON files (HomePage.json for this example) and the data are in:
- Checkout.xslx for DDT
- Checkout.feature for BDT

Some issues related on reporting might need more investigation (it seems there are some 
differences between JUnit and TestNG in integration with Cucumber)