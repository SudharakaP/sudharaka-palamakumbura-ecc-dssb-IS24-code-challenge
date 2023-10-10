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

describe('ScrumMaster e2e test', () => {
  const scrumMasterPageUrl = '/scrum-master';
  const scrumMasterPageUrlPattern = new RegExp('/scrum-master(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const scrumMasterSample = {};

  let scrumMaster;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/scrum-masters+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/scrum-masters').as('postEntityRequest');
    cy.intercept('DELETE', '/api/scrum-masters/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (scrumMaster) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/scrum-masters/${scrumMaster.id}`,
      }).then(() => {
        scrumMaster = undefined;
      });
    }
  });

  it('ScrumMasters menu should load ScrumMasters page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('scrum-master');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ScrumMaster').should('exist');
    cy.url().should('match', scrumMasterPageUrlPattern);
  });

  describe('ScrumMaster page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(scrumMasterPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ScrumMaster page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/scrum-master/new$'));
        cy.getEntityCreateUpdateHeading('ScrumMaster');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', scrumMasterPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/scrum-masters',
          body: scrumMasterSample,
        }).then(({ body }) => {
          scrumMaster = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/scrum-masters+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/scrum-masters?page=0&size=20>; rel="last",<http://localhost/api/scrum-masters?page=0&size=20>; rel="first"',
              },
              body: [scrumMaster],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(scrumMasterPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ScrumMaster page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('scrumMaster');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', scrumMasterPageUrlPattern);
      });

      it('edit button click should load edit ScrumMaster page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ScrumMaster');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', scrumMasterPageUrlPattern);
      });

      it('edit button click should load edit ScrumMaster page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ScrumMaster');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', scrumMasterPageUrlPattern);
      });

      it('last delete button click should delete instance of ScrumMaster', () => {
        cy.intercept('GET', '/api/scrum-masters/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('scrumMaster').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', scrumMasterPageUrlPattern);

        scrumMaster = undefined;
      });
    });
  });

  describe('new ScrumMaster page', () => {
    beforeEach(() => {
      cy.visit(`${scrumMasterPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ScrumMaster');
    });

    it('should create an instance of ScrumMaster', () => {
      cy.get(`[data-cy="name"]`).type('Turkey Regional').should('have.value', 'Turkey Regional');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        scrumMaster = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', scrumMasterPageUrlPattern);
    });
  });
});
