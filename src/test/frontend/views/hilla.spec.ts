import { test, expect } from '@playwright/test';

function getUrl() {
  return 'http://localhost:8080/hilla';
}

test.beforeEach(async ({ page }) => {
  await page.goto(getUrl());
  await page.getByLabel('Username').fill("user");
  await page.getByLabel('Password', { exact: true }).fill("user");
  await page.getByRole('button', { name: 'Log in' }).click();
  await page.waitForURL(new RegExp(`${getUrl()}(\\?continue)?`));
  await page.waitForTimeout(300);
});

test('clicking button shows notification', async ({ page }) => {
  await expect(await page.locator('p').count()).toBeLessThan(1);
  await page.getByRole('button', { name: 'Say hello' }).click();
  await expect(await page.locator('p').first()).toBeVisible();
});

test('clicking button twice shows two notifications', async ({ page }) => {
  await page.getByRole('button', { name: 'Say hello' }).click();
  await page.locator('p').nth(0).waitFor()
  await page.getByRole('button', { name: 'Say hello' }).click();
  await page.locator('p').nth(1).waitFor()
  await expect(await page.locator('p').count()).toBe(2);
});

test('click button shows Hello Anonymous User notification when user name is empty', async ({ page }) => {
  await page.getByRole('button', { name: 'Say hello' }).click();
  await expect(await page.locator('p')).toContainText('Hello anonymous user');
});

test('click button shows Hello User notification when user is not empty', async ({ page }) => {
  await page.getByLabel('Your name').fill('Vaadiner');
  await page.getByRole('button', { name: 'Say hello' }).click();
  await expect(page.locator('p')).toContainText('Hello Vaadiner');
});

test('user enters name and clicks button', async ({ page }) => {
  await page.getByLabel('Your name').click();
  await page.getByLabel('Your name').fill('Jane Smith');
  await page.getByRole('button', { name: 'Say hello' }).click();
  await expect(page.locator('p').first()).toBeVisible();
  await expect(page.locator('p')).toContainText('Hello Jane Smith');
});

