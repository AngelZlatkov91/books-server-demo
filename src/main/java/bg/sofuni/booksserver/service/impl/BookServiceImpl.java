package bg.sofuni.booksserver.service.impl;

import bg.sofuni.booksserver.model.AuthorEntity;
import bg.sofuni.booksserver.model.BookEntity;
import bg.sofuni.booksserver.model.dto.AuthorDTO;
import bg.sofuni.booksserver.model.dto.BookDTO;
import bg.sofuni.booksserver.repository.AuthorRepository;
import bg.sofuni.booksserver.repository.BookRepository;
import bg.sofuni.booksserver.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
     return bookRepository.findAll()
              .stream()
              .map(BookServiceImpl::mapBookToDTo)
              .toList();

    }

    @Override
    public Optional<BookDTO> findBookById(Long id) {
       return   bookRepository.findById(id).map(BookServiceImpl::mapBookToDTo);

    }

    @Override
    public void deleteBookById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Long createBook(BookDTO bookDTO) {
       AuthorEntity byName = this.authorRepository.findByName(bookDTO.getAuthor().getName()).orElse(null);
        AuthorEntity authorEntity = new AuthorEntity();
        if (byName == null) {
            authorEntity = new AuthorEntity();
            authorEntity.setName(bookDTO.getAuthor().getName());
            authorEntity = authorRepository.save(authorEntity);
        }
        BookEntity book = new BookEntity();
          book.setAuthor(authorEntity);
          book.setIsbn(bookDTO.getIsbn());
          book.setTitle(bookDTO.getTitle());

           book = bookRepository.save(book);

        return book.getId();
    }


    private static BookDTO mapBookToDTo(BookEntity bookEntity) {
        AuthorDTO authorEntity = new AuthorDTO();
        authorEntity.setName(bookEntity.getAuthor().getName());

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookEntity.getId());
        bookDTO.setTitle(bookEntity.getTitle());
        bookDTO.setIsbn(bookEntity.getIsbn());
        bookDTO.setAuthor(authorEntity);
        return bookDTO;
    }
}
