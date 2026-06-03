// package com.heavenscode.rac.domain;

// import jakarta.persistence.Column;
// import jakarta.persistence.Embeddable;
// import java.io.Serializable;
// import java.util.Objects;

// @Embeddable
// public class AutojobsinvoicelinesId implements Serializable {

//     @Column(name = "InvocieID")
//     private Long invocieId;

//     @Column(name = "LineID")
//     private Integer lineId;

//     public AutojobsinvoicelinesId() {}

//     public AutojobsinvoicelinesId(Long invocieId, Integer lineId) {
//         this.invocieId = invocieId;
//         this.lineId = lineId;
//     }

//     public Long getInvocieId() {
//         return invocieId;
//     }

//     public void setInvocieId(Long invocieId) {
//         this.invocieId = invocieId;
//     }

//     public Integer getLineId() {
//         return lineId;
//     }

//     public void setLineId(Integer lineId) {
//         this.lineId = lineId;
//     }

//     @Override
//     public boolean equals(Object o) {
//         if (this == o) return true;
//         if (!(o instanceof AutojobsinvoicelinesId)) return false;
//         AutojobsinvoicelinesId that = (AutojobsinvoicelinesId) o;
//         return Objects.equals(invocieId, that.invocieId)
//             && Objects.equals(lineId, that.lineId);
//     }

//     @Override
//     public int hashCode() {
//         return Objects.hash(invocieId, lineId);
//     }
// }
