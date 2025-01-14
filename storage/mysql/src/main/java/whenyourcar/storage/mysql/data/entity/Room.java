package whenyourcar.storage.mysql.data.entity;

import jakarta.persistence.*;
import lombok.*;
import whenyourcar.storage.mysql.data.common.DateBaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Room extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="roomId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1Id") // user1을 참조하는 외래키 컬럼
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2Id") // user2을 참조하는 외래키 컬럼
    private User user2;
}
