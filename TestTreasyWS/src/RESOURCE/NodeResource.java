package RESOURCE;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import CONTROL.NodeC;
import FRAMEWORK.CALLBACK.BusinessRuleException;
import FRAMEWORK.CALLBACK.ListReturn;
import FRAMEWORK.CALLBACK.ObjectReturn;
import MODEL.Constants;
import MODEL.GenericReturn;
import MODEL.NodeM;

@Path("/node")
public class NodeResource {
	private NodeC control;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericReturn listarTodos() throws Exception {
		control = new NodeC();
		GenericReturn retorno = new GenericReturn();
		control.listarTodos(new NodeM(), new ListReturn<NodeM>() {
			@Override
			public void Callback(List<NodeM> list) {
				retorno.setSucess(true);
				if (list != null && !list.isEmpty()) {
					executaFilhos(list);
					retorno.setList(list);
				} else {
					retorno.setMessage(Constants.NENHUM_REGISTRO_ENCONTRADO);
				}
			}

			@Override
			public void CallbackException(BusinessRuleException e) {
				retorno.setSucess(true);
				retorno.setMessage(e.getMessage());
			}
		});
		return retorno;
	}

	private void executaFilhos(List<NodeM> list) {
		for (NodeM nodeM : list) {
			adicionaFilhos(nodeM, nodeM);
		}
	}

	private void adicionaFilhos(NodeM pai, NodeM filho) {
		if (filho.getCd() != pai.getCd()) {
			NodeM node = new NodeM();
			node.setCd(pai.getCd());
			node.setCode(pai.getCode());
			node.setDescription(pai.getDescription());
			node.setDetail(pai.getDetail());
			filho.setParentId(node);
			pai.addChildren(filho);
		}
		if (pai.getParentId() != null)
			adicionaFilhos(pai.getParentId(), filho);
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericReturn adicionar(String json) throws Exception {
		control = new NodeC();
		GenericReturn retorno = new GenericReturn();

		NodeM node = convertJsonToNode(json, false);

		control.adicionar(node, new ObjectReturn<Integer>() {
			@Override
			public void Callback(Integer Object) {
				NodeM node = new NodeM();
				node.setCd(Object);
				retorno.setObject(node);
				retorno.setSucess(true);
				retorno.setMessage(Constants.INSERIDO_COM_SUCESSO);
			}

			@Override
			public void CallbackException(BusinessRuleException e) {
				retorno.setSucess(true);
				retorno.setMessage(e.getMessage());
			}
		});
		return retorno;
	}

	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public GenericReturn atualizar(String json) throws Exception {
		control = new NodeC();
		GenericReturn retorno = new GenericReturn();
		NodeM node = convertJsonToNode(json, true);

		control.atualizar(node, new ObjectReturn<NodeM>() {
			@Override
			public void Callback(NodeM Object) {
				NodeM node = new NodeM();
				node.setCd(Object.getCd());
				retorno.setObject(node);
				retorno.setSucess(true);
				retorno.setMessage(Constants.ATUALIZADO_COM_SUCESSO);
			}

			@Override
			public void CallbackException(BusinessRuleException e) {
				retorno.setSucess(true);
				retorno.setMessage(e.getMessage());
			}
		});
		return retorno;
	}

	@GET
	@Path("/{parentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public GenericReturn buscaFilhos(@PathParam("parentId") int parentId) throws Exception {
		control = new NodeC();
		GenericReturn retorno = new GenericReturn();
		control.listarTodos(new NodeM(), new ListReturn<NodeM>() {
			@Override
			public void Callback(List<NodeM> list) {
				retorno.setSucess(true);
				if (list != null && !list.isEmpty()) {
					executaFilhos(list);
					for (NodeM nodeM : list) {
						if (nodeM.getCd() == parentId) {
							nodeM.setHasChildren(true);
							retorno.setList(nodeM.getChildren());
							break;
						} else
							retorno.setMessage(Constants.NENHUM_REGISTRO_ENCONTRADO);
					}
				}
			}

			@Override
			public void CallbackException(BusinessRuleException e) {
				retorno.setSucess(true);
				retorno.setMessage(e.getMessage());
			}
		});
		return retorno;
	}

	private NodeM convertJsonToNode(String json, boolean atualizar) {
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(json).getAsJsonObject();

		String code = obj.get("code").getAsString();
		String description = obj.get("description").getAsString();
		String detail = obj.get("detail").getAsString();

		NodeM node = new NodeM();
		node.setCode(code);
		node.setDescription(description);
		node.setDetail(detail);

		if (atualizar) {
			int cd = obj.get("cd").getAsInt();
			node.setCd(cd);
		}
		if (obj.get("parentId") != null) {
			NodeM nodepai = new NodeM();
			nodepai.setCd(obj.get("parentId").getAsInt());
			node.setParentId(nodepai);
		}
		return node;
	}

//	public static void main(String[] args) {
//		NodeM obj3 = new NodeM();
//		obj3.setCode("Código 3");
//		obj3.setDescription("Filho do 1");
//		obj3.setDetail("Filho do 1");
//
//		NodeM nodepai = new NodeM();
//		nodepai.setCd(4);
//		obj3.setParentId(nodepai);
//
//		obj3.setParentId(nodepai);
//		Gson gson = new Gson();
//		String json = gson.toJson(obj3);
//		System.out.println(json);
//
//		String jsonInString = "{\"cd\":0,\"descricao\":\"teste\",\"posicaoX\":10,\"posicaoY\":6}";
//		JsonParser parser = new JsonParser();
//		JsonObject obj = parser.parse(jsonInString).getAsJsonObject();
//		String imgurl = obj.get("descricao").getAsString();
//
//		System.out.println(imgurl);
//	}

}
