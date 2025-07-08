package com.example.backend.api;

import com.example.backend.dto.ClassGroupDTO;
import com.example.backend.dto.request.ClassRequest;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.service.IClassService;
import com.example.backend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/classes")
public class ClassApi {
    @Autowired
    private IClassService classService;

    @GetMapping("/all")
    public ResponseEntity<PageResponse<ClassGroupDTO>> getAllClasses() {
        PageResponse<ClassGroupDTO> response = classService.getAllClasses();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/paged")
    public ResponseEntity<PageResponse<ClassGroupDTO>> getAllClassesByPaged(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        PageResponse<ClassGroupDTO> response = classService.getAllClassesByPaged(pageIndex, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<PageResponse<ClassGroupDTO>> filterClasses(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String classCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String teacherName,
            @RequestParam(required = false) Constants.ClassStatus status,
            @RequestParam(required = false) Constants.LearningMode learningMode,
            @RequestParam(required = false) String courseName
    ) {
        PageResponse<ClassGroupDTO> response = classService.filterClasses(
                pageIndex, pageSize, classCode, name, teacherName, status, learningMode, courseName
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-class")
    public ResponseEntity<?>createClass(@RequestBody ClassRequest request) {
        try{
            ClassGroupDTO created = classService.createClass(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Lỗi khi tạo yêu cầu bồi thường: " + e.getMessage());
        }
    }

    @PutMapping("/update/{classCode}")
    public ResponseEntity<?> updateClass(
            @PathVariable String classCode,
            @RequestBody ClassRequest request
    ) {
        try {
            ClassGroupDTO updated = classService.updateClass(classCode,
                    request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi cập nhật lớp học: " + e.getMessage());
        }
    }
}
