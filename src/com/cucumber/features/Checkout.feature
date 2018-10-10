Feature:Checkout 
	Scenario Outline:Search for item using RS Stock Reference
		When I fill in "<SearchLocator>" with "<RSStockReference>"
		Then The Item Page Is Showing The Text "<Text>" Located By "<ItemNameLocator>"
		
	Examples:
			|RSStockReference| SearchLocator | ItemNameLocator | Text |
			|168-4545		 | searchTerm    |prodDescription|Fuji Electric 2MBI400U2B-060-50, M233 Series IGBT Module, 400 A max, 650 V, Panel Mount|
			|168-3906		 | searchTerm	 |prodDescription|JKL Components White Backlight, CCFL 3mm dia x, 100mm Variable Brightness|
			|534-9978		 | searchTerm    |prodDescription|JKL Components White Backlight, CCFL 3mm dia x, 100mm Variable Brightness|
			|268-3907		 | searchTerm	 |   zeroResult  |We couldn't find any results for '268-3907'|
			
	Scenario Outline:Add an item to the basket
		Given Go To HomePage 
		When I fill in "searchTerm" with "<RSStockReference>"
		And I click Up quantity "<N times>" times
		And I click on the "addToBasket" button
		Then Verify the basket amount
	
	Examples:
		|RSStockReference| N times 	|
		|168-3906		 | 		2	|
		|168-3906		 |      3   |
		
		
	