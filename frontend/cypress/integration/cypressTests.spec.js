describe('Webpage is loaded', () => {
    it('Webpage is up!', () => {
        cy.visit('http://localhost:3000')
    })
})

describe('Backend is available', () => {
    it('Backend works!', () => {
        cy.visit('http://localhost:3000')

        cy.get('#root > div > div:nth-child(3) > table > tbody > tr:nth-child(1)')
    })
})

describe('Add a new user', () => {
    it('New user added!', () => {
        cy.visit('http://localhost:3000')

        cy.get('#root > div > div.input > div:nth-child(2) > div > input').type('Run János')
        cy.get('#root > div > div.input > div:nth-child(3) > div > input').type('10')
        cy.get('#root > div > div.input > div:nth-child(4) > div > input').type('Szolnok')

        cy.get('#root > div > div.input > button').contains('Send').click()
    })
})

describe('Delete the first user', () => {
    it('First user deleted!', () => {
        cy.visit('http://localhost:3000')

        cy.get('#root > div > div:nth-child(3) > table > tbody > tr:nth-child(4) > td:nth-child(2)').contains('Run János')
        cy.get('#root > div > div:nth-child(3) > table > tbody > tr:nth-child(4) > td:nth-child(3)').contains('10')
        cy.get('#root > div > div:nth-child(3) > table > tbody > tr:nth-child(4) > td:nth-child(4)').contains('Szolnok')

        cy.get('#root > div > div:nth-child(3) > table > tbody > tr:nth-child(4) > td:nth-child(5) > button').contains('Delete').click()
    })
})