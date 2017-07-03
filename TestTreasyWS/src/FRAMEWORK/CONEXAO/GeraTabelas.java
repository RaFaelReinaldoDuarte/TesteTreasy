package FRAMEWORK.CONEXAO;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import CONTROL.NodeC;
import FRAMEWORK.CALLBACK.BusinessRuleException;
import FRAMEWORK.CALLBACK.ObjectReturn;
import MODEL.NodeM;

public class GeraTabelas {

	public static void main(String[] args) throws Exception {
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.addAnnotatedClass(NodeM.class);

		SchemaExport se = new SchemaExport(cfg);
		se.create(true, true);

		NodeC control = new NodeC();
		NodeM obj1= new NodeM();
		obj1.setCode("Código 1");
		obj1.setDescription("Descrição 1");
		obj1.setDetail("Detalhe 1");
		
		control.adicionar(obj1, new ObjectReturn<Integer>() {
			@Override
			public void Callback(Integer Object) {
				System.out.println("Id do cadastro "+Object);
			}
			@Override
			public void CallbackException(BusinessRuleException e) {
				System.out.println(e.getMessage());
			}
		});
		
		NodeM obj2 = new NodeM();
		obj2.setCode("Código 2");
		obj2.setDescription("Descrição 2");
		obj2.setDetail("Detalhe 2");
		control.adicionar(obj2, new ObjectReturn<Integer>() {
			@Override
			public void Callback(Integer Object) {
				System.out.println("Id do cadastro "+Object);
			}
			@Override
			public void CallbackException(BusinessRuleException e) {
				System.out.println(e.getMessage());
			}
		});
		
		NodeM obj3 = new NodeM();
		obj3.setCode("Código 3");
		obj3.setDescription("Filho do 1");
		obj3.setDetail("Filho do 1");
		obj3.setParentId(obj1);
		control.adicionar(obj3, new ObjectReturn<Integer>() {
			@Override
			public void Callback(Integer Object) {
				System.out.println("Id do cadastro "+Object);
			}
			@Override
			public void CallbackException(BusinessRuleException e) {
				System.out.println(e.getMessage());
			}
		});
		
		NodeM obj4 = new NodeM();
		obj4.setCode("Código 4");
		obj4.setDescription("Filho do 3");
		obj4.setDetail("Filho do 3");
		obj4.setParentId(obj3);
		control.adicionar(obj4, new ObjectReturn<Integer>() {
			@Override
			public void Callback(Integer Object) {
				System.out.println("Id do cadastro "+Object);
			}
			@Override
			public void CallbackException(BusinessRuleException e) {
				System.out.println(e.getMessage());
			}
		});
		
		NodeM obj5 = new NodeM();
		obj5.setCode("Código 5");
		obj5.setDescription("Filho do 1");
		obj5.setDetail("Filho do 1");
		obj5.setParentId(obj1);
		control.adicionar(obj5, new ObjectReturn<Integer>() {
			@Override
			public void Callback(Integer Object) {
				System.out.println("Id do cadastro "+Object);
			}
			@Override
			public void CallbackException(BusinessRuleException e) {
				System.out.println(e.getMessage());
			}
		});
		System.out.println("CRIADO COM SUCESSO");
		
	}
	
	/**
	CREATE OR REPLACE FUNCTION get_all_children_array(use_parent INT4) RETURNS INT4[] AS $$
	DECLARE
	    process_parents INT4[] := ARRAY[ use_parent ];
	    children INT4[] := '{}';
	    new_children INT4[];
	BEGIN
	    WHILE ( array_upper( process_parents, 1 ) IS NOT NULL ) LOOP
	        new_children := ARRAY( SELECT cd FROM nodem WHERE parentid = ANY( process_parents ) AND cd <> ALL( children ) );
	        children := children || new_children;
	        process_parents := new_children;
	    END LOOP;
	    RETURN children;
	END;
	$$ LANGUAGE plpgsql
	SELECT get_all_children_array(5);

	SELECT * FROM nodem 
	WHERE cd = any(get_all_children_array(1)) 
	OR cd=1;
**/

	
}	
