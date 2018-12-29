package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.NotNull;
import play.data.format.Formats;

@Entity
@Table( name="produto", 
	    uniqueConstraints = {@UniqueConstraint(name = "uq_produto", columnNames = {"descricao", "idproduto"})})
public class Produto extends Model{
	@Id
	@Column(name = "idproduto")
	private Integer codigo;
	
	@NotNull
	@Size(max = 100)
	private String descricao;
	
	private boolean ativo;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    private Date dataCadastro = new Date();
	
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static final Finder<Long, Produto> find = new Finder<>(Produto.class);
}
