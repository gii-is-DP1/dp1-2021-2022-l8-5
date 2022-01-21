package org.springframework.dwarf.worker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dwarf.board.BoardService;
import org.springframework.dwarf.game.CreateGameWhilePlayingException;
import org.springframework.dwarf.game.Game;
import org.springframework.dwarf.game.GameService;
import org.springframework.dwarf.player.Player;
import org.springframework.dwarf.player.PlayerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author David Zamora
 * @author Jose Ignacio Garcia
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter({ Service.class, Component.class }))
public class WorkerServiceTest {

	@Autowired
	private WorkerService workerService;
	@Autowired
	protected PlayerService playerService;

	@Autowired
	protected BoardService boardService;

	@Autowired
	protected GameService gameService;

	@Test
	public void testCountWithInitialData() {
		int count = workerService.WorkerCount();
		assertEquals(count, 2);
	}

	@Test
	public void testFindAll() {
		Iterable<Worker> workers = this.workerService.findAll();
		assertThat(workers.spliterator().getExactSizeIfKnown()).isEqualTo(2);
	}

	@Test
	public void testFindByWorkerId() {
		int id = 1;

		Optional<Worker> Worker = workerService.findByWorkerId(id);
		Worker p = Worker.orElse(null);
		assertThat(p.getXposition()).isNull();
	}

	@Test
	public void testFindByWorkerIdNegative() {
		int id = 50;

		Optional<Worker> Worker = workerService.findByWorkerId(id);
		assertTrue(Worker.isEmpty());
	}

	@Test
	public void testFindByPlayerId() {
		int id = 1;

		Collection<Worker> Worker = workerService.findByPlayerId(id);
		assertThat(Worker.size()).isEqualTo(2);
	}

	@Test
	public void testFindByPlayerIdNegative() {
		int id = 50;

		Collection<Worker> Worker = workerService.findByPlayerId(id);
		assertThat(Worker.size()).isEqualTo(0);
	}

	@Test
	public void testFindNotPlacedByPlayerIdAndGameId() {
		int pid = 1;
		int gid = 1;

		Collection<Worker> Worker = workerService.findNotPlacedByPlayerIdAndGameId(pid, gid);
		assertThat(Worker.size()).isEqualTo(1);

	}

	@Test
	public void testFindNotPlacedByPlayerIdAndGameIdNegative() {
		int pid = 2;
		int gid = 1;

		Collection<Worker> Worker = workerService.findNotPlacedByPlayerIdAndGameId(pid, gid);
		assertThat(Worker.size()).isEqualTo(0);

	}

	@Test
	public void tesFfindNotPlacedByGameId() {
		int gid = 1;

		Collection<Worker> Worker = workerService.findNotPlacedByGameId(gid);
		assertThat(Worker.size()).isEqualTo(1);

	}

	@Test
	public void tesFfindNotPlacedByGameIdNegative() {
		int gid = 2;

		Collection<Worker> Worker = workerService.findNotPlacedByGameId(gid);
		assertThat(Worker.size()).isEqualTo(0);

	}

	@Test
	public void testFindNotPlacedAidByGameId() {
		int gid = 1;

		Collection<Worker> Worker = workerService.findNotPlacedAidByGameId(gid);
		assertThat(Worker.size()).isEqualTo(1);

	}

	@Test
	public void testFindNotPlacedAidByGameIdNegative() {
		int gid = 2;

		Collection<Worker> Worker = workerService.findNotPlacedAidByGameId(gid);
		assertThat(Worker.size()).isEqualTo(0);

	}

	@Test
	public void tesFfindPlacedByGameId() {
		int gid = 1;

		Collection<Worker> Worker = workerService.findPlacedByGameId(gid);
		assertThat(Worker.size()).isEqualTo(1);

	}

	@Test
	public void tesFfindPlacedByGameIdNegative() {
		int gid = 2;

		Collection<Worker> Worker = workerService.findPlacedByGameId(gid);
		assertThat(Worker.size()).isEqualTo(0);

	}

	@Test
	public void testFindByPlayerIdAndGameId() {
		int pid = 1;
		int gid = 1;

		Collection<Worker> Worker = workerService.findByPlayerIdAndGameId(pid, gid);
		assertThat(Worker.size()).isEqualTo(2);

	}

	@Test
	public void testFindByPlayerIdAndGameIdNegative() {
		int pid = 1;
		int gid = 2;

		Collection<Worker> Worker = workerService.findByPlayerIdAndGameId(pid, gid);
		assertThat(Worker.size()).isEqualTo(0);

	}

	@Test
	public void testDeletePlayerWorker() {
		Player player = playerService.findPlayerById(1);

		workerService.deletePlayerWorker(player);
		Collection<Worker> Worker = workerService.findByPlayerId(1);
		assertThat(Worker.size()).isEqualTo(0);
	}

	@Test
	public void testSaveWorker() throws IllegalPositionException {
		Worker WorkerTest = new Worker();
		WorkerTest.setStatus(true);
		WorkerTest.setXposition(3);
		WorkerTest.setYposition(2);

		workerService.saveWorker(WorkerTest);
		int id = WorkerTest.getId();

		Optional<Worker> Worker = workerService.findByWorkerId(id);
		Worker p = Worker.orElse(null);
		assertEquals(p.getXposition(), 3);
	}

	@ParameterizedTest
	@CsvSource({
			"-1,-1",
			"-1,2",
			"-1,4",
			"2,-1",
			"2,4",
			"3,3",
			"4,3",
			"4,4"
	})
	public void testInvalidWorker(int xpos, int ypos) {
		Worker WorkerTest = new Worker();
		WorkerTest.xposition = xpos;
		WorkerTest.yposition = ypos;

		Boolean res = workerService.getWorkerInvalid(WorkerTest);
		assertEquals(res, true);
	}

	@ParameterizedTest
	@CsvSource({
			"0,0",
			"1,1",
			"3,2",
			"2,2"
	})
	public void testValidWorker(int xpos, int ypos) {
		Worker WorkerTest = new Worker();
		WorkerTest.xposition = xpos;
		WorkerTest.yposition = ypos;

		Boolean res = workerService.getWorkerInvalid(WorkerTest);
		assertEquals(res, false);
	}

	@Test
	public void testNullWorker() {
		Worker WorkerTest = new Worker();
		WorkerTest.xposition = null;
		WorkerTest.yposition = 2;

		Boolean res = workerService.getWorkerInvalid(WorkerTest);

		assertEquals(res, false);
	}

	@Test
	public void testNullWorker2() {
		Worker WorkerTest = new Worker();
		WorkerTest.xposition = 2;
		WorkerTest.yposition = null;

		Boolean res = workerService.getWorkerInvalid(WorkerTest);

		assertEquals(res, false);
	}

	@Test
	public void testSaveWorkerInvalid() {
		Worker WorkerTest = new Worker();
		WorkerTest.xposition = -1;
		WorkerTest.yposition = 2;

		assertThrows(IllegalPositionException.class, () -> {
			workerService.saveWorker(WorkerTest);
		});
	}

	@Test
	public void testCreatePlayerWorkers() throws IllegalPositionException {

		Player player = playerService.findPlayerById(1);
		Game game = new Game();
		game.setId(1);

		int count = workerService.findByPlayerIdAndGameId(1, 1).size();

		workerService.createPlayerWorkers(player, game, 1);

		int countAfter = workerService.findByPlayerIdAndGameId(1, 1).size();

		assertEquals(count + 2, countAfter);

	}

	@Test
	public void testDeleteWorker() {
		int id = 1;

		Optional<Worker> Worker = workerService.findByWorkerId(id);
		Worker p = Worker.orElse(null);
		workerService.delete(p);
		Worker deletedWorker = workerService.findByWorkerId(id).orElse(null);
		assertEquals(deletedWorker, null);
	}

	@Test
	public void testDeleteWorker2()
			throws DataAccessException, IllegalPositionException, CreateGameWhilePlayingException {

		Player p = playerService.findPlayerById(3);

		Game g = new Game();

		g.setFirstPlayer(p);
		g.setCurrentPlayer(p);

		Worker w1 = new Worker(p, g, 4);

		gameService.saveGame(g);

		boardService.createBoard(g);

		w1.setId(5);
		w1.setXposition(1);
		w1.setYposition(0);
		w1.setPlayer(p);

		workerService.saveWorker(w1);

		workerService.deletePlayerWorker(w1.getPlayer());
		Worker deletedWorker = workerService.findByWorkerId(5).orElse(null);
		assertThat(deletedWorker).isEqualTo(null);
	}

	@Test
	public void testDeleteAidWorker() throws DataAccessException, IllegalPositionException {

		Player p = playerService.findPlayerById(4);
		Game g = new Game();
		g.setId(1);

		Worker w = new Worker(p, g, 4);

		workerService.saveWorker(w);

		List<Worker> Workers = workerService.findPlayerAidWorkers(p.getId());

		Collection<Worker> WorkersNotAid = workerService.findByPlayerId(p.getId());

		assertThat(WorkersNotAid.size()).isEqualTo(1);

		assertThat(Workers.size()).isEqualTo(1);

		Workers.stream().forEach(x -> workerService.deletePlayerAidWorkers(p));

		List<Worker> WorkersAfter = workerService.findPlayerAidWorkers(p.getId());

		assertThat(WorkersAfter.size()).isEqualTo(0);

	}
}