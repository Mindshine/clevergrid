package com.mindshine.clevergrid.mongo;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ReflectionUtils;

import com.mindshine.clevergrid.model.Topic;

public class CascadeingMongoEventListener extends AbstractMongoEventListener<Topic> {

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Topic> event) {

		ReflectionUtils.doWithFields(event.getSource().getClass(), field -> {
			ReflectionUtils.makeAccessible(field);
			if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
				final Object value = field.get(event.getSource());
				DbRefFieldCallback callback = new DbRefFieldCallback();

				ReflectionUtils.doWithFields(value.getClass(), callback);

				if (!callback.isIdFound()) {
					throw new MappingException("Cannot perform cascade save on child object without id set");
				}

				this.mongoOperations.save(value);

			}

		});
	}

	private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {

		private boolean idFound;

		@Override
		public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
			ReflectionUtils.makeAccessible(field);
			if (field.isAnnotationPresent(Id.class)) {
				this.idFound = true;
			}
		}

		public boolean isIdFound() {
			return this.idFound;
		}
	}

}
