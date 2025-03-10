package com.example.todoapp.data.repositories

import com.example.todoapp.data.database.dao.CategoriesDao
import com.example.todoapp.data.database.dao.TaskDao
import com.example.todoapp.data.mapper.CategoryMapper
import com.example.todoapp.data.mapper.TaskMapper
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.data.models.TaskWithCategoryColor
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TaskRepositoryTest {

    private lateinit var taskRepository: TaskRepository

    @MockK
    private lateinit var taskDao: TaskDao

    @MockK
    private lateinit var categoryDao: CategoriesDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        taskRepository = TaskRepository(
            taskDao = taskDao,
            categoryDao = categoryDao
        )
    }

    @Test
    fun `getTasksColor should return flow of tasks empty`() = runTest {
        // Given
        val expectedTasks = listOf<TaskWithCategoryColor>()
        coEvery { taskDao.getTasksWithCategoryColor() } returns flowOf(expectedTasks)

        // When
        val result = taskRepository.getTasksColor()

        // Then
        assertEquals(expectedTasks, result.first())
        coVerify(exactly = 1) { taskDao.getTasksWithCategoryColor() }
    }

    @Test
    fun `getTasksColor should return flow of tasks with category color`() = runTest {
        // Given
        val expectedTasks = MotherObjectRep.listOfTasks

        coEvery { taskDao.getTasksWithCategoryColor() } returns flowOf(expectedTasks)

        // When
        val result = taskRepository.getTasksColor()

        // Then

        assertEquals(expectedTasks, result.first())
        coVerify(exactly = 1) { taskDao.getTasksWithCategoryColor() }
    }

    @Test
    fun `getTasksColor should emit multiple values`() = runTest {
        // Given
        val firstTasks = listOf<TaskWithCategoryColor>()
        val secondTasks = MotherObjectRep.listOfTasks

        coEvery { taskDao.getTasksWithCategoryColor() } returns flowOf(firstTasks, secondTasks)

        // When
        val result = taskRepository.getTasksColor()

        // Then
        assertEquals(firstTasks, result.first())  // Primero se emite la lista vacía
        assertEquals(secondTasks, result.toList()[1])  // Luego se emite la lista con tareas
        coVerify(exactly = 1) { taskDao.getTasksWithCategoryColor() }
    }

    @Test
    fun `createTask should call dao with mapped task`(): Unit = runTest {

        val taskModel = MotherObjectRep.taskModel

        val taskRoomModel = TaskMapper.toTaskRoomModel(taskModel)
        coEvery { taskDao.createTask(taskRoomModel) } just Runs

        // When
        taskRepository.createTask(taskModel)

        // Then
        coVerify(exactly = 1) { taskDao.createTask(taskRoomModel) }
    }

    @Test
    fun `updateTask should call dao with mapped task`(): Unit = runTest {

        val taskModel = MotherObjectRep.taskModel

        val taskRoomModel = TaskMapper.toTaskRoomModel(taskModel)
        coEvery { taskDao.updateTask(taskRoomModel) } just Runs

        // When
        taskRepository.updateTask(taskModel)

        // Then
        coVerify(exactly = 1) { taskDao.updateTask(taskRoomModel) }
    }

    @Test
    fun `deleteTask should call dao with mapped task`(): Unit = runTest {

        val taskModel = MotherObjectRep.taskModel

        val taskRoomModel = TaskMapper.toTaskRoomModel(taskModel)
        coEvery { taskDao.deleteTask(taskRoomModel) } just Runs

        // When
        taskRepository.deleteTask(taskModel)

        // Then
        coVerify(exactly = 1) { taskDao.deleteTask(taskRoomModel) }
    }


    @Test
    fun `getCategoriesCountTask should return flow of categories empty`() = runTest {
        // Given
        val expectedTasks = listOf<CategoryWithTaskCount>()
        coEvery { categoryDao.getTaskCountByCategory() } returns flowOf(expectedTasks)

        // When
        val result = taskRepository.getCategoriesCountTask()

        // Then
        assertEquals(expectedTasks, result.first())
        coVerify(exactly = 1) { categoryDao.getTaskCountByCategory() }
    }

    @Test
    fun `getCategoriesCountTask should return flow of CategoryWithTaskCount`() = runTest {
        // Given
        val expectedCategories = MotherObjectRep.listOfCategories

        coEvery { categoryDao.getTaskCountByCategory() } returns flowOf(expectedCategories)

        // When
        val result = taskRepository.getCategoriesCountTask()

        // Then

        assertEquals(expectedCategories, result.first())
        coVerify(exactly = 1) { categoryDao.getTaskCountByCategory() }
    }

    @Test
    fun `createCategory should call dao with mapped task`(): Unit = runTest {

        val categoryModel = MotherObjectRep.categoryModel

        val categoryRoomModel = CategoryMapper.toCategoryRoomModel(categoryModel)
        coEvery { categoryDao.createCategory(categoryRoomModel) } just Runs

        // When
        taskRepository.createCategory(categoryModel)

        // Then
        coVerify(exactly = 1) { categoryDao.createCategory(categoryRoomModel) }
    }

    @Test
    fun `updateCategory should call dao with mapped task`(): Unit = runTest {

        val categoryModel = MotherObjectRep.categoryModel

        val categoryRoomModel = CategoryMapper.toCategoryRoomModel(categoryModel)
        coEvery { categoryDao.updateCategory(categoryRoomModel) } just Runs

        // When
        taskRepository.updateCategory(categoryModel)

        // Then
        coVerify(exactly = 1) { categoryDao.updateCategory(categoryRoomModel) }
    }

    @Test
    fun `deleteCategory should call dao with mapped task`(): Unit = runTest {

        val categoryModel = MotherObjectRep.categoryModel

        val categoryRoomModel = CategoryMapper.toCategoryRoomModel(categoryModel)
        coEvery { categoryDao.deleteCategory(categoryRoomModel) } just Runs

        // When
        taskRepository.deleteCategory(categoryModel)

        // Then
        coVerify(exactly = 1) { categoryDao.deleteCategory(categoryRoomModel) }
    }
}