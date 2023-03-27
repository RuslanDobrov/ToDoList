package todolist.services.interfaces;

import todolist.domain.Tag;

public interface ITagService {
    Tag findOrCreate(Tag tag);
}
