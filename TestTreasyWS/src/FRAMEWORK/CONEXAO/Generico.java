package FRAMEWORK.CONEXAO;

import org.hibernate.MappingException;
import org.hibernate.cfg.AnnotationConfiguration;

import MODEL.NodeM;

public class Generico {

    private static AnnotationConfiguration cfg;

    public static AnnotationConfiguration getAnnotationConfiguration() {
        try {
            if (cfg == null) {
                cfg = new AnnotationConfiguration();
                cfg.addAnnotatedClass(NodeM.class);
            }
            return cfg;
        } catch (MappingException e) {
            getAnnotationConfiguration();
        }
        return cfg;
    }
}
