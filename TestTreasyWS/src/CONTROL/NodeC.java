package CONTROL;

import DAO.NodeDAO;
import FRAMEWORK.CALLBACK.ListReturn;
import FRAMEWORK.CALLBACK.ObjectReturn;
import MODEL.NodeM;

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