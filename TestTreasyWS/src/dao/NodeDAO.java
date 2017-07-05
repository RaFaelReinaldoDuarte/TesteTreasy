package dao;

import framework.callback.BusinessRuleException;
import framework.callback.ListReturn;
import framework.callback.ObjectReturn;
import model.NodeM;

public class NodeDAO {
	protected CRUD<NodeM> crud;

	public NodeDAO() {
		crud = CRUD.getInstance();
	}

	public void adicionar(NodeM obj, ObjectReturn<Integer> callback) {
		try {
			crud.insert(obj, callback);
		} catch (Exception erro) {
			callback.CallbackException(new BusinessRuleException(erro.getMessage()));
		}
	}

	public void atualizar(NodeM obj, ObjectReturn<NodeM> callback) {
		try {
			crud.update(obj, callback);
		} catch (Exception erro) {
			callback.CallbackException(new BusinessRuleException(erro.getMessage()));
		}
	}

	public void listarTodos(NodeM obj, ListReturn<NodeM> callback) {
		try {
			crud.listarTodos(obj, callback);
		} catch (Exception erro) {
			callback.CallbackException(new BusinessRuleException(erro.getMessage()));
		}
	}

}
