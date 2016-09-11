package web.agil.fixture
/**
 * Created by mikael on 10/09/16.
 */
class FixtureHelper {

    static checkExists(Class domain, Closure closure) {
        if (domain.count() == 0)
            closure()
    }

    static createInstance(Map params, Class domain) {
        def instance = domain.newInstance()
        instance.properties = params
        instance.save(failOnError: true)
    }

}
