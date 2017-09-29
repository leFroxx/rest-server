package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Hero;
import app.service.HeroService;

@RestController
@CrossOrigin
public class HeroController {
	@Autowired
	HeroService heroService;

	@RequestMapping(value = "/heroes", method = RequestMethod.GET)
	public List<Hero> getAllHeroes() {
		List<Hero> heroes = heroService.getAllHeroes();
		return heroes;
	}

	@RequestMapping(value = "/heroes/{id}", method = RequestMethod.GET)
	public Hero getById(@PathVariable Long id) {
		Hero hero = heroService.getById(id);
		return hero;
	}

	@RequestMapping(value = "/heroes/{id}", method = RequestMethod.PUT)
	public Hero update(@PathVariable Long id, @RequestBody Hero hero) {
		heroService.updateHero(hero);
		return hero;
	}

	@RequestMapping(value = "/heroes/add", method = RequestMethod.POST)
	public Hero create(@RequestBody Hero hero) {
		heroService.saveHero(hero);
		return hero;
	}

	@RequestMapping(value = "/heroes/{id}", method = RequestMethod.DELETE)
	public Hero delete(@PathVariable Long id) {
		heroService.deleteHero(id);
		return null;
	}
}