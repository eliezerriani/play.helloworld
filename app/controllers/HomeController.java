package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import model.Produto;
import play.db.Database;
import play.db.NamedDatabase;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
	private Database db;
	private Database db2;
    
	@Inject
	public HomeController(Database db, @NamedDatabase("db2") Database db2){
		this.db = db;
		this.db2 = db2;
	}
	
	private List<Produto> utilizarJDBCDireto(Integer codigoProduto, String descricao) throws SQLException{
		List<Produto> produtos = new ArrayList<Produto>();
		
		Connection con = db.getConnection();
		try {
    		con.createStatement().execute(" CREATE TABLE IF NOT EXISTS PRODUTO(CODIGO INTEGER, DESCRICAO TEXT)");
    		
    		PreparedStatement stmt = con.prepareStatement(" INSERT INTO PRODUTO(IDPRODUTO,DESCRICAO) VALUES(?,?)");
	    		    	    	
	    	stmt.setInt(1, codigoProduto);
	    	stmt.setString(2, descricao);
	    	
	    	stmt.executeUpdate();
	    	
	        ResultSet rs = con.createStatement().executeQuery(" SELECT * FROM PRODUTO ");
	    	
	    	while (rs.next()) {
	    		Produto prod = new Produto();
	    		prod.setCodigo(rs.getInt("IDPRODUTO"));
	    		prod.setDescricao(rs.getString("DESCRICAO"));
	    		
	    		produtos.add(prod);
	    	}
		}finally {
			con.close();
		}
	
		return produtos;
	}
	
	private List<Produto> utilizarEBean(Integer codigoProduto, String descricao){
		EbeanServer ebean2 = Ebean.getServer("db2");
		
		if(codigoProduto.intValue() > 0) {
			Produto prod = new Produto();
			prod.setAtivo(true);
			prod.setCodigo(codigoProduto);
			prod.setDataCadastro(new Date());
			prod.setDescricao(descricao);
			ebean2.save(prod);
			
			prod = new Produto();
			prod.setAtivo(true);
			prod.setCodigo(codigoProduto);
			prod.setDataCadastro(new Date());
			prod.setDescricao(descricao);
						
			EbeanServer db1 = Ebean.getDefaultServer();
			db1.beginTransaction();
			db1.save(prod);
			db1.rollbackTransaction();
		}
		
		
		
		List<Produto> produtos = new ArrayList<Produto>();
		
		produtos.addAll( ebean2.find(Produto.class).findList() );
		
		produtos.addAll( Produto.find.query().where().gt("idproduto", 1).findList() );
		
		return produtos;
	}
	
    public Result index(Integer codigoProduto, String descricao) {
    	try {
	    	//List<Produto> produtos = utilizarJDBCDireto(codigoProduto, descricao);
    		List<Produto> produtos = utilizarEBean(codigoProduto,descricao);
	    	return ok(views.html.index.render(produtos));
    	}catch(Exception e) {
    		return internalServerError("SQL: "+e.getMessage());
    	}
    }
    
    public Result salvar() {
    	return ok("Ok");
    }

}
