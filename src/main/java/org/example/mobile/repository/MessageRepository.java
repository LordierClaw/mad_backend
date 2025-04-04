package org.example.mobile.repository;

import org.example.mobile.entity.Doctor;
import org.example.mobile.entity.Message;
import org.example.mobile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
    List<Message> findByReceiverId(Long receiverId);

    List<Message> findBySenderId(Long receiverId);

    //
    @Query("SELECT m FROM Message m WHERE " +
                  "(m.senderId = :user1 AND m.receiverId = :user2) OR " +
                  "(m.senderId = :user2 AND m.receiverId = :user1) " +
                  "ORDER BY m.sentAt ASC")
    List<Message> getConversationBetweenUsers(@Param("user1") Long user1Id,
                                              @Param("user2") Long user2Id);

    @Query("SELECT u FROM User u JOIN Prescription p ON u.id = p.userId WHERE p.doctorId = :doctorId")
    List<User> getAllUsersForDoctor(@Param("doctorId") Long doctorId);

    @Query("SELECT d FROM Doctor d JOIN Prescription p ON d.id = p.doctorId WHERE p.userId = :userId")
    List<Doctor> getAllDoctorsForUser(@Param("userId") Long userId);

}
