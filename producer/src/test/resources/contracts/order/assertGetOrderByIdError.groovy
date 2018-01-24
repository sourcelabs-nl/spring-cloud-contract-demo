package contracts.order

import org.springframework.cloud.contract.spec.Contract

/**
 * Contract definition, written in a Groovy DSL
 */
Contract.make {

    /**
     * Request response which succeeds, completes HTTP status code 200
     */
    request {
        method 'GET'
        url('/orders/500')
        headers {
            accept("application/json")
        }
    }
    response {
        status 500
    }
}