package ch.ergon.lernende.wmtippspiel.backend;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.codegen.GeneratorStrategy;
import org.jooq.meta.Definition;
import org.jooq.meta.TableDefinition;

public class WmTippspielGeneratorStrategy extends DefaultGeneratorStrategy {

    @Override
    public String getJavaClassName(Definition definition, GeneratorStrategy.Mode mode) {
        var defaultName = super.getJavaClassName(definition, mode);
        if (mode == GeneratorStrategy.Mode.DEFAULT && definition instanceof TableDefinition) {
            return defaultName + "Table";
        } else {
            return defaultName;
        }
    }
}