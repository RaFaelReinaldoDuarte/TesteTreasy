package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import framework.callback.BusinessRuleException;
import framework.callback.ListReturn;
import framework.callback.ObjectReturn;
import framework.conexao.Generico;
import model.Constants;

/**
 * @author reinaldo.duarte
 * @param <E>
 */
public class CRUD<E> {

	private Session sessao = null;
	private SessionFactory fabrica = null;

	private CRUD() {
	}

	private static CRUD instance = null;

	public static CRUD getInstance() {
		if (instance == null) {
			instance = new CRUD();
		}
		return instance;
	}

	public void insert(final E entidade, final ObjectReturn<Integer> callback) throws Exception {
		try {
			AnnotationConfiguration gen = Generico.getAnnotationConfiguration();
			fabrica = gen.buildSessionFactory();
			sessao = fabrica.openSession();

			sessao.beginTransaction();
			// sessao.save(entidade);
			sessao.saveOrUpdate(entidade);
			// sessao.persist(entidade);
			sessao.flush();
			sessao.getTransaction().commit();
			sessao.close();
			fabrica.close();
			GetLastInsert(entidade.getClass().getSimpleName(), new ObjectReturn<Integer>() {
				@Override
				public void Callback(Integer Object) {
					callback.Callback(Object);
				}

				@Override
				public void CallbackException(BusinessRuleException e) {
					callback.CallbackException(
							new BusinessRuleException(Constants.ERRO_AO_BUSCAR_DADOS + " " + e));
				}
			});

		} catch (Exception erro) {
			callback.CallbackException(new BusinessRuleException(Constants.ERRO_AO_INSERIR + " " + erro));
		}
	}
	
	public void update(final E entidade , final ObjectReturn<E> callback) throws Exception {
		try {
			AnnotationConfiguration gen = Generico.getAnnotationConfiguration();
			fabrica = gen.buildSessionFactory();
			sessao = fabrica.openSession();
			sessao.beginTransaction();
			sessao.saveOrUpdate(entidade);
			sessao.flush();
			sessao.getTransaction().commit();
			sessao.close();
			fabrica.close();
			callback.Callback(entidade);
		} catch (Exception erro) {
			callback.CallbackException(new BusinessRuleException(Constants.ERRO_AO_INSERIR + " " + erro));
		}
	}

	/**
	 * @param entidade
	 *            , recebe o entity e da delete no banco
	 * @param callback
	 * @throws java.lang.Exception
	 */
	public void delete(final E entidade, final ObjectReturn<Boolean> callback) throws Exception {
		try {
			AnnotationConfiguration gen = Generico.getAnnotationConfiguration();
			fabrica = gen.buildSessionFactory();
			sessao = fabrica.openSession();
			sessao.beginTransaction();
			sessao.delete(entidade);
			// sessao.flush();
			sessao.getTransaction().commit();
			sessao.close();
			fabrica.close();
			callback.Callback(true);
		} catch (Exception erro) {
			sessao.close();
			fabrica.close();
			callback.CallbackException(new BusinessRuleException(Constants.ERRO_AO_DELETAR + erro));
		}
	}

	/**
	 * @param entidade
	 *            , classe entity
	 * @param id
	 *            , em object.
	 * @return retorna um entity preenchido, caso tenha dados no banco.
	 */
	public void buscarPorId(final E entidade, final Object id, final ObjectReturn<E> callback) throws Exception {
		AnnotationConfiguration gen = Generico.getAnnotationConfiguration();
		try {
			fabrica = gen.buildSessionFactory();
			sessao = fabrica.openSession();
			sessao.beginTransaction();
			// sessao.getTransaction().commit();
			Object a = sessao.get(entidade.getClass(), (Serializable) id);
			// sessao.flush();
			sessao.close();
			fabrica.close();
			callback.Callback((E) a);
		} catch (Exception erro) {
			sessao.close();
			fabrica.close();
			callback.CallbackException(new BusinessRuleException(Constants.ERRO_AO_BUSCAR_DADOS + erro));
		}
	}

	/**
	 * @param entidade
	 * @return a uma lista com todos os registros da entidade
	 * @throws java.lang.Exception
	 */
	public void listarTodos(final E entidade, final ListReturn<E> callback) throws Exception {
		AnnotationConfiguration gen = Generico.getAnnotationConfiguration();
		try {
			fabrica = gen.buildSessionFactory();
			sessao = fabrica.openSession();
			sessao.beginTransaction();

			List<E> list = new ArrayList<>();
			list = sessao.createCriteria(entidade.getClass()).list();
			// sessao.getTransaction().commit();
			sessao.close();
			fabrica.close();
			callback.Callback(list);
		} catch (Exception e) {
			sessao.close();
			fabrica.close();
			callback.CallbackException(new BusinessRuleException(Constants.ERRO_AO_BUSCAR_DADOS + e));
		}
	}

	/**
	 * MÉTODO USADO PARA FAZER UM HQL
	 *
	 * @param entity
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public void buscaSQLList(final String hql, final ListReturn<E> callback) throws Exception {
		AnnotationConfiguration gen = Generico.getAnnotationConfiguration();
		try {
			fabrica = gen.buildSessionFactory();
			sessao = fabrica.openSession();
			sessao.beginTransaction();
			Query queryResult = sessao.createQuery(hql);

			List<E> list = new ArrayList<>();
			list = queryResult.list();
			sessao.getTransaction().commit();
			sessao.flush();
			sessao.close();
			fabrica.close();
			if (list.size() > 0) {
				callback.Callback(list);
			} else {
				callback.Callback(null);
			}
		} catch (Exception erro) {
			callback.CallbackException(new BusinessRuleException(Constants.ERRO_AO_BUSCAR_DADOS + erro));
		}
	}

	public void buscaSQLObject(final String hql, final ObjectReturn<E> callback) throws Exception {
		AnnotationConfiguration gen = Generico.getAnnotationConfiguration();
		try {
			fabrica = gen.buildSessionFactory();
			sessao = fabrica.openSession();
			sessao.beginTransaction();
			Query listagem_query = sessao.createQuery(hql);
			Object retorno = new ArrayList<>();
			retorno = listagem_query.uniqueResult();
			sessao.getTransaction().commit();
			sessao.close();
			fabrica.close();
			callback.Callback((E) retorno);
		} catch (Exception erro) {
			callback.CallbackException(new BusinessRuleException(Constants.ERRO_AO_BUSCAR_DADOS + erro));
		}
	}

	protected void GetLastInsert(final String classe, final ObjectReturn<Integer> callback) throws Exception {
		String query = "select max(cd) from " + classe;
		try {
			buscaSQLObject(query, (ObjectReturn<E>) new ObjectReturn<Integer>() {
				@Override
				public void Callback(Integer Object) {
					callback.Callback(Object);
				}

				@Override
				public void CallbackException(BusinessRuleException e) {
					callback.CallbackException(new BusinessRuleException(Constants.ERRO_AO_BUSCAR_DADOS + e));
				}
			});
		} catch (Exception ex) {
			callback.CallbackException(new BusinessRuleException(Constants.ERRO_AO_BUSCAR_DADOS + ex));
		}
	}
}
