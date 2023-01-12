package at.ac.fhsalzburg.swd.spring.model.medias.visitors;

public interface VisitableMedia {
    public abstract void accept(MediaVisitor mediaVisitor);
}
