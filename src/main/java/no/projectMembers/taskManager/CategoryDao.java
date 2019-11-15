package no.projectMembers.taskManager;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDao extends AbstractDao<Category>{

        public CategoryDao(DataSource dataSource){
            super(dataSource);
        }

        public void insert(Category category, PreparedStatement statement) throws SQLException {
            insert(category, "INSERT INTO categories (NAME) VALUES (?)");
        }

        @Override
        protected void insertObject(Category category, PreparedStatement statement) throws SQLException{
            statement.setString(1, category.getName());
        }


        public List<Category> listAll() throws SQLException {
            return listAll("SELECT * FROM categories");
        }

        @Override
        protected Category readObject(ResultSet resultSet) throws SQLException{
            Category category = new Category();
            category.setId(resultSet.getInt("Id"));
            category.setName(resultSet.getString("Name"));
            return category;
        }
}
