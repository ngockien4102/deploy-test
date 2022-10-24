package doan.oishii_share_cong_thuc_nau_an.Exception;

import doan.oishii_share_cong_thuc_nau_an.dto.Responds.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ExceptionResponse handleAllException(Exception ex, WebRequest request) {
//        // quá trình kiểm soat lỗi diễn ra ở đây
//        return new ExceptionResponse(80801, ex.getLocalizedMessage());
//    }
//
//    @ExceptionHandler(BadRequestException.class)
//    public BadRequestException handleNotFoundException(ExceptionResponse ex, WebRequest request) {
//        // quá trình kiểm soat lỗi diễn ra ở đây
//        return new BadRequestException(new ExceptionResponse(ErrorCode.notFound));
//    }
//
//    @ExceptionHandler({ NotFoundException.class })
//    public ResponseEntity<Object> handleAccessDeniedException(
//            Exception ex, WebRequest request) {
//        return new ResponseEntity<Object>(
//                "not found", new HttpHeaders(), HttpStatus.FORBIDDEN);
//    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return new ExceptionResponse(ErrorCode.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleBadCredentialsExceptionException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return new ExceptionResponse(ErrorCode.INTERNAL_SERVER_ERROR, "Tên người dùng hoặc mật khẩu sai");
    }

//    @ExceptionHandler(BadRequestException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ExceptionResponse handleBadRequestException(BadRequestException ex, WebRequest request) {
//         return new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
//    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException ex, WebRequest request) {
        return new ExceptionResponse(ErrorCode.Not_Found, ex.getMessage());
    }


}
