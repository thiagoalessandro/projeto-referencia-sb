package br.com.packagebase.projetoreferenciasb.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Audited(auditParents = AbstractEntity.class)
@Table(name = "tbl_configuracao", uniqueConstraints = @UniqueConstraint(name = "uk_configuracao_nome", columnNames = "nome"))
public class Configuracao extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Nome é obrigatório")
    @Column(name = "nome", length = 25, nullable = false)
    private String nome;

    @NotNull(message = "Valor é obrigatório")
    @Column(name = "valor", length = 50, nullable = false)
    private String valor;

}
