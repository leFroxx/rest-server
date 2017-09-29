package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.HeroDao;
import app.model.Hero;

@Service("HeroService")
@Transactional
public class HeroService {

	@Autowired
	private HeroDao dao;

	public Hero getById(Long id) {
		return dao.getById(id);
	}

	public Hero getByName(String name) {
		Hero Hero = dao.getByName(name);
		return Hero;
	}

	public void saveHero(Hero hero) {
		dao.save(hero);
	}

	public void updateHero(Hero hero) {
		Hero entity = dao.getById(hero.getId());
		if (entity != null) {
			entity.setId(hero.getId());
			entity.setName(hero.getName());
		}
	}

	public void deleteHero(Long id) {
		dao.delete(id);
	}

	public List<Hero> getAllHeroes() {
		return dao.getAllHeroes();
	}

	public boolean isHeroNameUnique(Long id, String name) {
		Hero hero = getByName(name);
		return (hero == null || ((id != null) && (hero.getId() == id)));
	}

}