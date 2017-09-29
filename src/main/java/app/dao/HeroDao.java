package app.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import app.model.Hero;

@Repository("HeroDao")
public class HeroDao extends AbstractDao<Long, Hero> {

	public Hero getById(Long id) {
		Hero Hero = getByKey(id);
		return Hero;
	}

	public Hero getByName(String name) {
		try {
			Hero hero = (Hero) getEntityManager().createQuery("FROM Hero h WHERE h.name = :name")
					.setParameter("name", name).getSingleResult();

			return hero;
		} catch (NoResultException ex) {
			return null;
		}
	}

	public void save(Hero Hero) {
		super.save(Hero);
	}

	public void delete(Long id) {
		Hero hero = (Hero) getEntityManager().createQuery("FROM Hero h WHERE h.id = :id").setParameter("id", id)
				.getSingleResult();
		delete(hero);
	}

	public List<Hero> getAllHeroes() {
		@SuppressWarnings("unchecked")
		List<Hero> heroes = getEntityManager().createQuery("FROM Hero h ORDER BY h.name ASC").getResultList();
		return heroes;
	}

}