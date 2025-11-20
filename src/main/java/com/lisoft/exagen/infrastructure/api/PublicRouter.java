package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.ClosedQuestionRequestDto;
import com.lisoft.exagen.application.dtos.ClosedQuestionResponseDto;
import com.lisoft.exagen.application.dtos.OpenQuestionRequestDto;
import com.lisoft.exagen.application.dtos.OpenQuestionResponseDto;
import com.lisoft.exagen.application.dtos.ResponseWrapper;
import com.lisoft.exagen.application.dtos.TestResponseDto;
import com.lisoft.exagen.infrastructure.mappers.ClosedQuestionMapper;
import com.lisoft.exagen.infrastructure.mappers.OpenQuestionMapper;
import com.lisoft.exagen.infrastructure.mappers.TestMapper;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.domain.models.OpenQuestion;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.domain.templates.services.ClosedQuestionService;
import com.lisoft.exagen.domain.templates.services.OpenQuestionService;
import com.lisoft.exagen.domain.templates.services.TestService;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import static com.lisoft.exagen.domain.utils.Constants.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "/public")
@CrossOrigin(origins = "*")
public class PublicRouter {

        private final TestService testService;
        private final OpenQuestionService openQuestionService;
        private final ClosedQuestionService closedQuestionService;

        public PublicRouter(TestService testService, OpenQuestionService openQuestionService,
                        ClosedQuestionService closedQuestionService) {
                this.openQuestionService = openQuestionService;
                this.closedQuestionService = closedQuestionService;
                this.testService = testService;
        }

        @GetMapping("/")
        public ResponseEntity<ResponseWrapper<String>> index() {
                return new ResponseEntity<>(
                                new ResponseWrapper<>(
                                                true,
                                                "Successful connection to exagen API",
                                                "Index router"),
                                HttpStatus.OK);
        }

        @GetMapping("/exams")
        public ResponseEntity<ResponseWrapper<List<TestResponseDto>>> getAllExams() {
                List<TestResponseDto> exams = testService.getAllTests().stream()
                                .map(TestMapper::toResponseDto)
                                .toList();
                return ResponseEntity.ok(
                                new ResponseWrapper<>(true, "Exams fetched successfully", exams));
        }

        @PostMapping("/exams")
        public ResponseEntity<ResponseWrapper<TestResponseDto>> createExam(@RequestBody TestResponseDto body) {
                Test test = new Test(
                                null,
                                body.title(),
                                body.userId(),
                                body.openQuestionIds(),
                                body.closedQuestionIds());

                Test created = testService.createTest(test);
                return new ResponseEntity<>(
                                new ResponseWrapper<>(
                                                true,
                                                "Exam created successfully",
                                                TestMapper.toResponseDto(created)),
                                HttpStatus.CREATED);
        }

        @PutMapping("/exams")
        public ResponseEntity<ResponseWrapper<TestResponseDto>> updateExam(@RequestBody TestResponseDto body) {
                Test test = new Test(
                                body.id(),
                                body.title(),
                                body.userId(),
                                body.openQuestionIds(),
                                body.closedQuestionIds());

                Test updated = testService.updateTest(body.id(), test);
                return new ResponseEntity<>(
                                new ResponseWrapper<>(
                                                true,
                                                "Exam updated successfully",
                                                TestMapper.toResponseDto(updated)),
                                HttpStatus.OK);
        }

        @DeleteMapping("/exams")
        public ResponseEntity<ResponseWrapper<TestResponseDto>> deleteExam(@RequestBody TestResponseDto body) {
                testService.deleteTest(body.id(), body.userId());
                return new ResponseEntity<>(
                                new ResponseWrapper<>(
                                                true,
                                                "Exam deleted successfully",
                                                null),
                                HttpStatus.OK);
        }

        @GetMapping("/questions")
        public ResponseEntity<ResponseWrapper<Object>> getAllQuestions() {
                List<OpenQuestionResponseDto> openQuestions = openQuestionService.getAllOpenQuestions()
                                .stream()
                                .map(OpenQuestionMapper::toResponseDto)
                                .toList();
                List<ClosedQuestionResponseDto> closedQuestions = closedQuestionService.getAllClosedQuestions()
                                .stream()
                                .map(ClosedQuestionMapper::toResponseDto)
                                .toList();

                Map<String, List<?>> data = Map.of(
                                "open", openQuestions,
                                "closed", closedQuestions);

                return new ResponseEntity<>(
                                new ResponseWrapper<>(
                                                true,
                                                "Successfully retrieved all questions",
                                                data),
                                HttpStatus.OK);
        }

        @PostMapping("/questions/open")
        public ResponseEntity<ResponseWrapper<OpenQuestionResponseDto>> createOpenQuestion(
                        @RequestBody OpenQuestionRequestDto body) {

                OpenQuestion created = openQuestionService.createOpenQuestion(OpenQuestionMapper.toModel(body));
                OpenQuestionResponseDto response = OpenQuestionMapper.toResponseDto(created);

                return new ResponseEntity<>(
                                new ResponseWrapper<>(
                                                true,
                                                "Successfully created open question",
                                                response),
                                HttpStatus.CREATED);
        }

        @PostMapping("/questions/closed")
        public ResponseEntity<ResponseWrapper<ClosedQuestionResponseDto>> createClosedQuestion(
                        @RequestBody ClosedQuestionRequestDto body) {

                ClosedQuestion created = closedQuestionService.createClosedQuestion(ClosedQuestionMapper.toModel(body));
                ClosedQuestionResponseDto response = ClosedQuestionMapper.toResponseDto(created);

                return new ResponseEntity<>(
                                new ResponseWrapper<>(
                                                true,
                                                "Successfully created closed question",
                                                response),
                                HttpStatus.CREATED);
        }

        @PutMapping("/questions/open")
        public ResponseEntity<ResponseWrapper<OpenQuestionResponseDto>> updateOpenQuestion(
                        @RequestBody OpenQuestionResponseDto body) {

                if (body.id() == null) {
                        return new ResponseEntity<>(
                                        new ResponseWrapper<>(false, "OpenQuestion ID cannot be null", null),
                                        HttpStatus.BAD_REQUEST);
                }

                OpenQuestion openQuestion = new OpenQuestion(
                                body.id(),
                                body.statement(),
                                body.response(),
                                body.userId(),
                                body.categoryId());
                OpenQuestion updated = openQuestionService.updateOpenQuestion(openQuestion);
                OpenQuestionResponseDto responseDto = new OpenQuestionResponseDto(
                                updated.id(),
                                updated.statement(),
                                updated.response(),
                                updated.userId(),
                                updated.categoryId());

                return new ResponseEntity<>(
                                new ResponseWrapper<>(
                                                true,
                                                "Open question updated successfully",
                                                responseDto),
                                HttpStatus.OK);
        }

        @PutMapping("/questions/closed")
        public ResponseEntity<ResponseWrapper<ClosedQuestionResponseDto>> updateClosedQuestion(
                        @RequestBody ClosedQuestionResponseDto body) {

                if (body.id() == null) {
                        return new ResponseEntity<>(
                                        new ResponseWrapper<>(false, "ClosedQuestion ID cannot be null", null),
                                        HttpStatus.BAD_REQUEST);
                }

                ClosedQuestion closedQuestion = new ClosedQuestion(
                                body.id(),
                                body.statement(),
                                body.options(),
                                body.correctAnswer(),
                                body.userId(),
                                body.categoryId());
                ClosedQuestion updated = closedQuestionService.updateClosedQuestion(closedQuestion);
                ClosedQuestionResponseDto responseDto = new ClosedQuestionResponseDto(
                                updated.id(),
                                updated.statement(),
                                updated.options(),
                                updated.correctAnswer(),
                                updated.userId(),
                                updated.categoryId());

                return new ResponseEntity<>(
                                new ResponseWrapper<>(
                                                true,
                                                "Open question updated successfully",
                                                responseDto),
                                HttpStatus.OK);
        }

        @DeleteMapping("/questions/open")
        public ResponseEntity<ResponseWrapper<OpenQuestionResponseDto>> deleteOpenQuestion(
                        @RequestBody OpenQuestionResponseDto dto) {

                openQuestionService.deleteOpenQuestion(dto.id(), dto.userId());

                return new ResponseEntity<>(
                                new ResponseWrapper<>(
                                                true,
                                                "Successfully deleted open question",
                                                null),
                                HttpStatus.OK);
        }

        @DeleteMapping("/questions/closed")
        public ResponseEntity<ResponseWrapper<ClosedQuestionResponseDto>> deleteClosedQuestion(
                        @RequestBody ClosedQuestionResponseDto dto) {

                closedQuestionService.deleteClosedQuestion(dto.id(), dto.userId());

                return new ResponseEntity<>(
                                new ResponseWrapper<>(
                                                true,
                                                "Successfully deleted closed question",
                                                null),
                                HttpStatus.OK);
        }
}
