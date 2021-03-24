# Mini-Shopify
## About
This project aims to create a platform, inspired by shopify, which allows signed-in users to create shops and any web user to browse and purchase products from said shops. The web application is only supported on JavaScript enables browsers.<br /> <br /> 
## Authors
- David Wang
- Derek Shao
- Hoang Bui
- Michael Fan
- Souheil Yazji
<br /> <br /> 
## Development Stack
1. FrontEnd: Vue.JS + BootstrapVue
2. Backend: Spring with REST endpoints
3. Database: PostgreSQL 
### [Travis-CI](https://travis-ci.com/github/mpfan/Mini-Shopify)
### [Heroku App](https://sh0p1fy.herokuapp.com/)
<br /> 

## Contribution Convention
Create a feature branch using main as base. Use the following link for [Commit Convention](https://www.conventionalcommits.org/en/v1.0.0/). All contributions to the project should pretain to an [Issue](https://github.com/mpfan/Mini-Shopify/issues), and should be logged in the Kanban project. Individual contributions to the project should be made through Pull Requests into the Main branch. Issues should be updated with the appropriate resolving pull request. 

## Run Instructions
To Pull and Run this code locally,

1. Install PostgreSQL. Make sure to install pgAdmin with it. When prompted, set the username and password to postgres/postgres
2. Create a "shopify" database from pgAdmin.
3. Open and build the project in IntelliJ
4. Use the "Run Mini Shopify" Run Configuration. When the application finishes starting up, it can be accessed at localhost:8080

To host the front end,

1. cd into shopify-client
2. run `yarn install` or `npm install`
3. run `yarn run serve` or `npm run serve`

## Project State
---
In milestone 1, the team started with the tasks of setting up the development stack to allow for agile development. The full development stack is configured, with a running TravisCI pipeline and heroku application also set up with a postgresSQL database. Most of the backend models have been completed. Controllers have been made to for shops and products. Front end components have been configured to route requests to the springboot backend controllers to be handled. The Vue.Js front end allows users to browse all shops, create a shop, modify a shop, create a product, modify a product and browse products in a shop using views. To see all completed issues in this task, please refer to the [Kanban Project](https://github.com/mpfan/Mini-Shopify/projects/1#column-13201543).

### Plan for Next Sprint
---
Some items to be completed by next sprint are:
1. User Authentication: this item will allow only authorized users to create/modify shops and products. It will also bind users to shops. This will likely include a login and signup view.
2. Shopping Cart: this item will allow shoppers to hold products in their cart for later checkout and modify their cart's contents. This will also include a cart view.

### UML Class Diagram
![SYSC4806_UML - UML Class](https://user-images.githubusercontent.com/17705586/111795913-067d0900-889e-11eb-841a-08c0ad2b8d43.png)

### E-R Diagram
This is a brief presentation of the Postgres database schema.
![SYSC4806_UML - ER diagram](https://user-images.githubusercontent.com/17705586/111795676-cf0e5c80-889d-11eb-80d8-6e5fb67cf04e.png)
