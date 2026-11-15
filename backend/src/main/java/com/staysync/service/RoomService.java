package com.staysync.service;

import com.staysync.model.Bed;
import com.staysync.model.Room;
import com.staysync.model.User;
import com.staysync.repository.BedRepository;
import com.staysync.repository.RoomRepository;
import com.staysync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Bed> getAllBeds() {
        return bedRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    @Transactional
    public Bed allocateBed(Long bedId, Long studentId) {
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new RuntimeException("Bed not found with id: " + bedId));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        if (Boolean.TRUE.equals(bed.getIsOccupied())) {
            throw new RuntimeException("Bed is already occupied");
        }

        bed.setIsOccupied(true);
        bed.setStudent(student);
        bed.setAssignedDate(LocalDate.now());

        Room room = bed.getRoom();
        if (room != null) {
            room.setOccupiedCount(room.getOccupiedCount() + 1);
            if (room.getOccupiedCount() >= room.getCapacity()) {
                room.setStatus("FULL");
            }
            roomRepository.save(room);

            student.setRoomNumber(room.getRoomNumber());
            student.setBedNumber(bed.getBedNumber());
            userRepository.save(student);
        }

        return bedRepository.save(bed);
    }

    @Transactional
    public Bed deallocateBed(Long bedId) {
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new RuntimeException("Bed not found with id: " + bedId));

        User student = bed.getStudent();
        if (student != null) {
            student.setRoomNumber(null);
            student.setBedNumber(null);
            userRepository.save(student);
        }

        bed.setIsOccupied(false);
        bed.setStudent(null);
        bed.setAssignedDate(null);

        Room room = bed.getRoom();
        if (room != null && room.getOccupiedCount() > 0) {
            room.setOccupiedCount(room.getOccupiedCount() - 1);
            room.setStatus("AVAILABLE");
            roomRepository.save(room);
        }

        return bedRepository.save(bed);
    }
}
