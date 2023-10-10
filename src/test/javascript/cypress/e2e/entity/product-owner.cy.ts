import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('ProductOwner e2e test', () => {
  const productOwnerPageUrl = '/product-owner';
  const productOwnerPageUrlPattern = new RegExp('/product-owner(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const productOwnerSample = {};

  let productOwner;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/product-owners+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/product-owners').as('postEntityRequest');
    cy.intercept('DELETE', '/api/product-owners/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (productOwner) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/product-owners/${productOwner.id}`,
      }).then(() => {
        productOwner = undefined;
      });
    }
  });

  it('ProductOwners menu should load ProductOwners page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('product-owner');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ProductOwner').should('exist');
    cy.url().should('match', productOwnerPageUrlPattern);
  });

  describe('ProductOwner page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(productOwnerPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ProductOwner page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/product-owner/new$'));
        cy.getEntityCreateUpdateHeading('ProductOwner');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productOwnerPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/product-owners',
          body: productOwnerSample,
        }).then(({ body }) => {
          productOwner = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/product-owners+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/product-owners?page=0&size=20>; rel="last",<http://localhost/api/product-owners?page=0&size=20>; rel="first"',
              },
              body: [productOwner],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(productOwnerPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ProductOwner page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('productOwner');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productOwnerPageUrlPattern);
      });

      it('edit button click should load edit ProductOwner page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ProductOwner');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productOwnerPageUrlPattern);
      });

      it('edit button click should load edit ProductOwner page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ProductOwner');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productOwnerPageUrlPattern);
      });

      it('last delete button click should delete instance of ProductOwner', () => {
        cy.intercept('GET', '/api/product-owners/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('productOwner').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', productOwnerPageUrlPattern);

        productOwner = undefined;
      });
    });
  });

  describe('new ProductOwner page', () => {
    beforeEach(() => {
      cy.visit(`${productOwnerPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ProductOwner');
    });

    it('should create an instance of ProductOwner', () => {
      cy.get(`[data-cy="name"]`).type('transmitting Clothing Future-proofed').should('have.value', 'transmitting Clothing Future-proofed');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        productOwner = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', productOwnerPageUrlPattern);
    });
  });
});
