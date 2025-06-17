describe('Vuetify Calendar Component', () => {
  const userID = 2; // Use a valid user ID with events in your backend or mock it

  beforeEach(() => {
    cy.visit(`/calendar/${userID}`);
  });

  it('shows loading initially and then displays events', () => {
    // Initially should show loading text
    cy.contains('Loading events...').should('be.visible');

    // Wait for loading to disappear (means events fetched or error)
    cy.contains('Loading events...').should('not.exist');

    // Check if error message is NOT shown
    cy.get('.error').should('not.exist');

    // Check if the calendar and events are rendered
    cy.get('.v-calendar').should('exist');

    // Events have class 'custom-event' from your template
    cy.get('.custom-event').should('exist');

    // Optionally check event titles/tooltips (based on event data)
    cy.get('.custom-event')
        .first()
        .invoke('attr', 'title')
        .should('not.be.empty');
  });

  it('can navigate months using buttons', () => {
    cy.get('.month-label').then(($label) => {
      const initialMonth = $label.text();

      cy.get('.month-controls button').first().click(); // Previous month

      cy.get('.month-label').should(($newLabel) => {
        expect($newLabel.text()).not.to.eq(initialMonth);
      });

      cy.get('.month-controls button').last().click(); // Next month (back to initial)
      cy.get('.month-label').should('contain', initialMonth);
    });
  });

  it('clicking "Today" button resets to current month', () => {
    cy.get('.today-button').click();
    const now = new Date();
    const expectedMonth = now.toLocaleDateString('en-US', { year: 'numeric', month: 'long' });
    cy.get('.month-label').should('contain', expectedMonth);
  });
});
