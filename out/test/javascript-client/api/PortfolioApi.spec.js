/*
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 *
 * Swagger Codegen version: 2.4.19
 *
 * Do not edit the class manually.
 *
 */

(function(root, factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD.
    define(['expect.js', '../../src/index'], factory);
  } else if (typeof module === 'object' && module.exports) {
    // CommonJS-like environments that support module.exports, like Node.
    factory(require('expect.js'), require('../../src/index'));
  } else {
    // Browser globals (root is window)
    factory(root.expect, root.ApiDocumentation);
  }
}(this, function(expect, ApiDocumentation) {
  'use strict';

  var instance;

  beforeEach(function() {
    instance = new ApiDocumentation.PortfolioApi();
  });

  describe('(package)', function() {
    describe('PortfolioApi', function() {
      describe('getPortfolio', function() {
        it('should call getPortfolio successfully', function(done) {
          // TODO: uncomment getPortfolio call and complete the assertions
          /*

          instance.getPortfolio(function(error, data, response) {
            if (error) {
              done(error);
              return;
            }
            // TODO: update response assertions
            expect(data).to.be.a(ApiDocumentation.Portfolio);
            {
              let dataCtr = data.holdings;
              expect(dataCtr).to.be.an(Array);
              expect(dataCtr).to.not.be.empty();
              for (let p in dataCtr) {
                let data = dataCtr[p];
                expect(data).to.be.a(ApiDocumentation.Holding);
                expect(data.stock).to.be.a('string');
                expect(data.stock).to.be("");
                expect(data.avgPrice).to.be.a('number');
                expect(data.avgPrice).to.be();
                expect(data.currentPrice).to.be.a('number');
                expect(data.currentPrice).to.be();
                expect(data.amount).to.be.a('number');
                expect(data.amount).to.be();
                expect(data.value).to.be.a('number');
                expect(data.value).to.be();

                      }
            }
            {
              let dataCtr = data.purchases;
              expect(dataCtr).to.be.an(Array);
              expect(dataCtr).to.not.be.empty();
              for (let p in dataCtr) {
                let data = dataCtr[p];
                expect(data).to.be.a(ApiDocumentation.Trade);
                expect(data.timestamp).to.be.a('string');
                expect(data.timestamp).to.be("");
                expect(data.stock).to.be.a('string');
                expect(data.stock).to.be("");
                expect(data.direction).to.be.a('string');
                expect(data.direction).to.be("buy");
                expect(data.price).to.be.a('number');
                expect(data.price).to.be();
                expect(data.quantity).to.be.a('number');
                expect(data.quantity).to.be();
                expect(data.value).to.be.a('number');
                expect(data.value).to.be();

                      }
            }
            {
              let dataCtr = data.liquidations;
              expect(dataCtr).to.be.an(Array);
              expect(dataCtr).to.not.be.empty();
              for (let p in dataCtr) {
                let data = dataCtr[p];
                expect(data).to.be.a(ApiDocumentation.Trade);
                expect(data.timestamp).to.be.a('string');
                expect(data.timestamp).to.be("");
                expect(data.stock).to.be.a('string');
                expect(data.stock).to.be("");
                expect(data.direction).to.be.a('string');
                expect(data.direction).to.be("buy");
                expect(data.price).to.be.a('number');
                expect(data.price).to.be();
                expect(data.quantity).to.be.a('number');
                expect(data.quantity).to.be();
                expect(data.value).to.be.a('number');
                expect(data.value).to.be();

                      }
            }
            expect(data.summary).to.be.a(Object);
            expect(data.summary).to.be();

            done();
          });
          */
          // TODO: uncomment and complete method invocation above, then delete this line and the next:
          done();
        });
      });
    });
  });

}));
