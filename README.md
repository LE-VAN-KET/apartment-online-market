# Ecommerce: Apartment Building Online Market
### Description brief:
<p>People who live in apartment buildings in large cities are quite accustomed to using forums 
or groups that the building administration or themselves have formed for everyone to post 
anything they wish to discuss. Although it may be quite annoying and unpleasant, selling 
items in those groups is also becoming more and more prevalent.</p>

<p>They could require a platform designed specifically for this purpose in order to sell, 
acquire, or rent their food, furniture, or other commodities.</p>

<p>So that, We could build a system that provide residents with an on-demand selling, 
purchasing, or renting experience that is transparent and offers almost 
seamless end-to-end service</p>

### Need to resolve the issues:
- How can a customer filter out things they don't want to see?
- How can retailers market their goods?
- How reliable are the information on the app, the vendors, and each other?
- When there is a disagreement, how can you find out the entire transaction history?
- How can the department management confirm who is the buyer and who is the seller?

### Prerequisites:
- [x] [Java 8+](https://www.oracle.com/java/technologies/downloads/#java8)
- [x] [Docker](https://www.docker.com/)
- [x] [Docker-compose](https://docs.docker.com/compose/install/)
- [x] [Maven](https://maven.apache.org/install.html)
### Start Application step by steps:
  * <h4>Open a terminal and inside **apartment-online-market** folder run:</h4>
    * <code>docker compose -f docker-compose.yaml up db_market redis logstash  -d</code>
    * <h5>After that:</h5>
    * <code>mvn clean install</code>
    * <h5>Finally:<h5>
    * <code>docker compose up -d --build</code>
        