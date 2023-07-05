package examsandquestions;

import java.time.LocalDate;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExamController {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/exams")
    public String listExams(Model model) {
        model.addAttribute("exams", examRepository.findAll(PageRequest.of(0, 1000, Sort.by("examDate").descending())));
        return "exams";
    }

    @GetMapping("/exams/{id}")
    public String viewExam(Model model, @PathVariable Long id) {
        model.addAttribute("exam", examRepository.getOne(id));
        model.addAttribute("questions", questionRepository.findAll());
        return "exam";
    }

    @PostMapping("/exams")
    public String addExam(@RequestParam String subject,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate examDate) {
        Exam exam = new Exam();
        exam.setSubject(subject);
        exam.setExamDate(examDate);
        examRepository.save(exam);
        return "redirect:/exams";
    }

    @PostMapping("/exams/{examId}/questions/{questionId}")
    @Transactional
    public String addQuestionToExam(@PathVariable Long examId, @PathVariable Long questionId) {
        Exam exam = examRepository.getOne(examId);
        Question question = questionRepository.getOne(questionId);
        if (!exam.getQuestions().contains(question)) {
            exam.getQuestions().add(question);
        }
        return "redirect:/exams/" + examId;
    }
}
