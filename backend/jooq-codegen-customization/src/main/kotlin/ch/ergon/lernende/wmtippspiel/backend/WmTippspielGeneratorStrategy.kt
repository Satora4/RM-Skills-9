package ch.ergon.lernende.wmtippspiel.backend

import org.jooq.codegen.DefaultGeneratorStrategy
import org.jooq.codegen.GeneratorStrategy
import org.jooq.codegen.GeneratorStrategy.Mode.DEFAULT
import org.jooq.meta.Definition
import org.jooq.meta.TableDefinition

class WmTippspielGeneratorStrategy : DefaultGeneratorStrategy() {

    override fun getJavaClassName(definition: Definition, mode: GeneratorStrategy.Mode): String {
        val defaultName = super.getJavaClassName(definition, mode)
        return when {
            mode == DEFAULT && definition is TableDefinition -> "${defaultName}Table"
            else                                             -> defaultName
        }
    }

}