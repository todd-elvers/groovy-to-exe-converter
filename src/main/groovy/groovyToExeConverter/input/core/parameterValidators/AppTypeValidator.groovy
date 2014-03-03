package groovyToExeConverter.input.core.parameterValidators

import groovy.util.OptionAccessor as Input

class AppTypeValidator {

    static def consoleAndGuiFlagsBothSet = { Input input ->
        return input.console && input.gui
    }

}
