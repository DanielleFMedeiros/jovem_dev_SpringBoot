package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.PilotoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Piloto {
	
	@Id
	@Column
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String name;
	
	@ManyToOne()
	private Pais pais;
	@ManyToOne()
	private Equipe equipe;
	
	public Piloto(PilotoDTO dto) {
		this(dto.getId(),
				 dto.getName(), 
				 new Pais(dto.getPaisId(), dto.getPaisName()),
				 new Equipe(dto.getEquipeId(), dto.getEquipeName()));
		}
		
		public PilotoDTO toDTO() {
			return new PilotoDTO(id, name, pais.getId(), pais.getName(), equipe.getId(), equipe.getName());
		}
	

}
