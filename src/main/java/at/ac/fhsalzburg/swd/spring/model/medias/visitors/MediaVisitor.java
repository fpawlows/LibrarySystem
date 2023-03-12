package at.ac.fhsalzburg.swd.spring.model.medias.visitors;

import at.ac.fhsalzburg.swd.spring.model.medias.*;

//Class NOT USED

public interface MediaVisitor {
    public abstract void visit(Book book);
    public abstract void visit(Movie movie);
    public abstract void visit(Paper paper);
    public abstract void visit(Audio audio);
}
