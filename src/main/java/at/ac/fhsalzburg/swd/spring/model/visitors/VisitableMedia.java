package at.ac.fhsalzburg.swd.spring.model.visitors;

import at.ac.fhsalzburg.swd.spring.model.medias.Media;

public interface VisitableMedia {
    public abstract void accept(MediaVisitor mediaVisitor);
}
