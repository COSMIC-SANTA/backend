package SANTA.backend.core.course.api;

import SANTA.backend.core.course.application.CourseService;
import SANTA.backend.core.course.dto.CourseRequest;
import SANTA.backend.core.mountain.dto.CompleteMountainRequest;
import SANTA.backend.global.common.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/course")
public class CourseApi {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ResponseHandler<Long>> saveCourse(@RequestBody CourseRequest courseRequest){
        Long courseId = courseService.saveCourse(courseRequest.mountain(), courseRequest.restaurants(), courseRequest.stays(), courseRequest.cafes(), courseRequest.spots());
        return ResponseEntity.ok().body(ResponseHandler.success(courseId));
    }

//    @PostMapping("/complete")
//    public ResponseEntity<ResponseHandler<Long>> updateCompleteMountain(@RequestBody CompleteMountainRequest completeMountainRequest){
//        Long updatedMountainId = courseService.updateCompleteCourse(completeMountainRequest.courseId(), completeMountainRequest.distance());
//        return ResponseEntity.ok(ResponseHandler.success(null));
//    }
}
