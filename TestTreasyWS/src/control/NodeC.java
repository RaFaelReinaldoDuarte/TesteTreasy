package control;

import dao.NodeDAO;
import framework.callback.ListReturn;
import framework.callback.ObjectReturn;
import model.NodeM;

public class NodeC {

	private NodeDAO dao;

	public void adicionar(NodeM obj, ObjectReturn<Integer> callback) throws Exception {
		dao = new NodeDAO();
		dao.adicionar(obj, callback);
	}

	public void atualizar(NodeM obj, ObjectReturn<NodeM> callback) throws Exception {
		dao = new NodeDAO();
		dao.atualizar(obj, callback);
	}

	public void listarTodos(NodeM obj, ListReturn<NodeM> callback) {
		dao = new NodeDAO();
		dao.listarTodos(obj, callback);
	}

}