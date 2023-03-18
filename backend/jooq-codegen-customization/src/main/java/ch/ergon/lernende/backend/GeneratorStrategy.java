package ch.ergon.lernende.backend;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;
import org.jooq.meta.TableDefinition;

public class GeneratorStrategy extends DefaultGeneratorStrategy {

    @Override
    public String getJavaClassName(Definition definition, org.jooq.codegen.GeneratorStrategy.Mode mode) {
        var defaultName = super.getJavaClassName(definition, mode);
        if (mode == org.jooq.codegen.GeneratorStrategy.Mode.DEFAULT && definition instanceof TableDefinition) {
            return defaultName + "Table";
        } else {
            return defaultName;
        }
    }
}
