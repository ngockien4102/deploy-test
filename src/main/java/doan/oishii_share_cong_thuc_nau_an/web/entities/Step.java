package doan.oishii_share_cong_thuc_nau_an.web.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Step" )
@Data
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "stepID"
//)
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Integer stepID;

    @Column(nullable=true, name = "describe", columnDefinition = "nvarchar(max)")
    private String describe;

    @Column(name = "title")
    private Integer title;

    @ManyToOne
    @JoinColumn(name = "formula_id", referencedColumnName = "formula_id", nullable = false)
//    @JsonBackReference
    private Formula formulaID;
}
