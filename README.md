### Exercise:

Using Java, write a simple program that calculates the price of a basket of shopping. 

Items are presented one at a time, in a list, identified by name - for example "Apple" or "Banana".

Multiple items are present multiple times in the list, so for example ["Apple", "Apple", "Banana"] is a basket with two apples and one banana.

#### Items are priced as follows:

- Apples are 35p each
- Bananas are 20p each
- Melons are 50p each, but are available as ‘buy one get one free’
- Limes are 15p each, but are available in a ‘three for the price of two’ offer

Given a list of shopping, calculate the total cost of those items


Use maven version 3.6.0

## To execute

- run ```mvn clean verify```
    - This will run the tests as well as static analysis tools (pmd, findbugs, jacoco)
    
## Notes

- I have used test stubs to replicate a datasource for the item prices and item discounts, this is used for the tests. I did not use a database, as this could be implemented later.
-