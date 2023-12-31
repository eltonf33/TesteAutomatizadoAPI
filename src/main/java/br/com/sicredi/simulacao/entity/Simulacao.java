package br.com.sicredi.simulacao.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "cpf_unique", columnNames = "cpf")
})
public class Simulacao {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Nome não pode ser vazio")
    private String nome;

    @NotNull(message = "CPF não pode ser vazio")
    private String cpf;

    @NotNull(message = "E-mail não deve ser vazio")
    @Email
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "E-mail deve ser um e-mail válido")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public Boolean getSeguro() {
        return seguro;
    }

    public void setSeguro(Boolean seguro) {
        this.seguro = seguro;
    }

    @NotNull(message = "Valor não pode ser vazio")
    @Max(value = 40000, message = "Valor deve ser menor ou igual a R$ 40.000")
    private BigDecimal valor;

    @NotNull(message = "Parcelas não pode ser vazio")
    @Min(value = 2, message = "Parcelas deve ser igual ou maior que 2")
    private Integer parcelas;

    @NotNull(message = "Uma das opções de Seguro devem ser selecionadas")
    private Boolean seguro;

}
